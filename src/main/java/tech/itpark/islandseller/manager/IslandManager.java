package tech.itpark.islandseller.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.itpark.islandseller.exception.NotFoundException;
import tech.itpark.islandseller.model.Island;
import tech.itpark.islandseller.exception.DataAccessException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class IslandManager {
    private final DataSource dataSource;

    public List<Island> getAll() {
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT id, name, region_id, country_id, price_eu, size_acr FROM islands ORDER BY id LIMIT 50"
                );
        ) {

            List<Island> items = new ArrayList<>();
            while (rs.next()) {
                items.add(new Island(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("region_id"),
                        rs.getInt("country_id"),
                        rs.getLong("price_eu"),
                        rs.getInt("size_acr")
                ));
            }
            return items;

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Island getById(long id) {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT id, name, region_id, country_id, price_eu, size_acr FROM islands WHERE id = ?"
                )
        ) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Island(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("region_id"),
                        rs.getInt("country_id"),
                        rs.getLong("price_eu"),
                        rs.getInt("size_acr")
                );
            }
            throw new NotFoundException();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

    }

    public Island search (String name) {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT id, name, region_id, country_id, price_eu, size_acr FROM islands WHERE name = ?"
                )
        ) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Island(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("region_id"),
                        rs.getInt("country_id"),
                        rs.getLong("price_eu"),
                        rs.getInt("size_acr")
                );
            }
            throw new NotFoundException();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

    }


    public List<Island> getByCountryId(int country_id) {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT id, name, region_id, country_id, price_eu, size_acr FROM islands WHERE country_id = ?"
                );
        ) {
            stmt.setInt(1, country_id);
            ResultSet rs = stmt.executeQuery();

            List<Island> items = new ArrayList<>();
            while (rs.next()) {
                items.add(new Island(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("region_id"),
                        rs.getInt("country_id"),
                        rs.getLong("price_eu"),
                        rs.getInt("size_acr")
                ));
            }
            return items;

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public List<Island> getByRegionId(int region_id) {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT id, name, region_id, country_id, price_eu, size_acr FROM islands WHERE region_id = ?"
                );
        ) {
            stmt.setInt(1, region_id);
            ResultSet rs = stmt.executeQuery();

            List<Island> items = new ArrayList<>();
            while (rs.next()) {
                items.add(new Island(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("region_id"),
                        rs.getInt("country_id"),
                        rs.getLong("price_eu"),
                        rs.getInt("size_acr")
                ));
            }
            return items;

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }



    public Island save(Island item) {
        if (item.getId() == 0) {
            try (
                    Connection conn = dataSource.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(
                            "INSERT INTO islands(name, region_id, country_id, price_eu, size_acr) VALUES (?, ? , ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS
                    );
            ) {
                int index = 0;
                stmt.setString(++index, item.getName());
                stmt.setInt(++index, item.getRegionId());
                stmt.setInt(++index, item.getCountryId());
                stmt.setLong(++index, item.getPrice());
                stmt.setInt(++index, item.getSize());
                stmt.execute();

                try (ResultSet keys = stmt.getGeneratedKeys();) {
                    if (keys.next()) {
                        int id = keys.getInt(1);
                        return getById(id);
                    }
                    throw new DataAccessException("No keys generated");
                }
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        }

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE islands SET name = ?, region_id = ?, country_id = ?, price_eu = ?, size_acr = ? WHERE id = ?"
                );
        ) {
            int index = 0;
            stmt.setString(++index, item.getName());
            stmt.setInt(++index, item.getRegionId());
            stmt.setInt(++index, item.getCountryId());
            stmt.setLong(++index, item.getPrice());
            stmt.setInt(++index, item.getSize());
            stmt.setInt(++index, item.getId());
            stmt.execute();

            return getById(item.getId());

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Island removeById(int id) {
        Island item = getById(id);

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM islands WHERE id = ?");
        ) {
            stmt.setInt(1, id);

            stmt.execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return item;
    }
}
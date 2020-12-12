package tech.itpark.islandseller.model;

import lombok.Data;
import lombok.Value;

@Value
public class Island {
    int id;
    String name;
    int regionId;
    int countryId;
    long price;
    int size;
}

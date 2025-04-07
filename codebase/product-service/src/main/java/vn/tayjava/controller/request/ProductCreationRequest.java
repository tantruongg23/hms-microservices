package vn.tayjava.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Getter
@ToString
public class ProductCreationRequest implements Serializable {
    private String name;
    private String description;
    private double price;
    private int userId;
}


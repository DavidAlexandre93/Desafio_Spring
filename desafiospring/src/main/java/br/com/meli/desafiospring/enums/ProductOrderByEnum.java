package br.com.meli.desafiospring.enums;

public enum ProductOrderByEnum {
    NAME_ASC(0),
    NAME_DESC(1),
    PRICE_ASC(2),
    PRICE_DESC( 3);

    Integer value;

    ProductOrderByEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
package model;

import lombok.Data;

@Data

public class Producto {

    private int IDPRO;
    private String NOMPRO;
    private Double PREPRO;
    private String DESPRO;
    private String ESTPRO;
    private String INGPRO;
    private int STOPRO = 1;
     
}

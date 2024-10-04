package com.alexiae.banking.component;


import org.springframework.stereotype.Component;

@Component
public class Variables {
  public static String MODEL_NOT_FOUND_COD = "SOAINT001";
  public static String MODEL_NOT_FOUND_MSG = "Recurso no encontrado.";

  public static String BAD_ARGUMENT_COD = "SOAINT002";
  public static String BAD_ARGUMENT_MSG = "Error en los argumentos(datos, parametros).";

  public static String GENERIC_COD = "SOAINT999";
  public static String GENERIC_MSG = "Error generico de la aplicación.";
}

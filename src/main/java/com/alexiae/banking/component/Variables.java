package com.alexiae.banking.component;


import org.springframework.stereotype.Component;

@Component
public class Variables {
  public static String MODEL_NOT_FOUND_COD = "ERROR001";
  public static String MODEL_NOT_FOUND_MSG = "Recurso no encontrado.";

  public static String BAD_ARGUMENT_COD = "ERROR002";
  public static String BAD_ARGUMENT_MSG = "Error en los argumentos(datos, parametros).";

  public static String USER_DUPLICATE = "ERROR003";
  public static String USER_DUPLICATE_MSG = "Error usuario duplicado";

  public static String GENERIC_COD = "ERROR999";
  public static String GENERIC_MSG = "Error generico de la aplicación.";

}

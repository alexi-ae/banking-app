package com.alexiae.banking.utils;

import java.util.Random;

public class AccountUtils {

  private AccountUtils() {
  }

  public static String numberAccountGenerate() {
    Random random = new Random();

    // Paso 1: Generar código de banco (3 dígitos)
    String codigoBanco = "191";// String.format("%03d", random.nextInt(1000)); // Ej: 001, 002, etc.

    // Paso 1: Generar código de banco (3 dígitos)
    String codigoSucursal = String.format("%03d", random.nextInt(1000)); // Ej: 001, 002, etc.

    // Paso 3: Generar número de cliente (8 dígitos)
    String numeroCliente = String.format("%08d", random.nextInt(100000000)); // Ej: 98765432

    // Paso 4: Calcular dígito verificador (usando módulo 11)
    String numeroBase = codigoBanco + codigoSucursal + numeroCliente;
    int digitoVerificador = calcularDigitoVerificador(numeroBase);

    // Concatenar todo para formar el número de cuenta
    return numeroBase + digitoVerificador;
  }

  private static int calcularDigitoVerificador(String numeroCuenta) {
    int suma = 0;
    int peso = 2; // Peso inicial

    // Recorrer los dígitos del número de cuenta de derecha a izquierda
    for (int i = numeroCuenta.length() - 1; i >= 0; i--) {
      int digito = Character.getNumericValue(numeroCuenta.charAt(i));
      suma += digito * peso;
      peso = (peso == 7) ? 2 : peso + 1; // Alternar los pesos entre 2 y 7
    }

    // Calcular residuo de la suma y determinar el dígito verificador
    int residuo = suma % 11;
    return (residuo == 0 || residuo == 1) ? 0 : 11 - residuo;
  }


  // Generar número de cuenta interbancario (CCI) para Perú
  public static String generarNumeroCCI(String numeroCuentaLocal, String codigoBanco) {
    // Paso 1: Concatenar el código del banco con el número de cuenta local
    String codigoSinVerificacion = codigoBanco + numeroCuentaLocal;

    // Paso 2: Calcular los dígitos de verificación (2 dígitos, usando módulo 97)
    String digitoVerificacion = calcularDigitoVerificadorCCI(codigoSinVerificacion);

    // Paso 3: Concatenar todo para formar el CCI (20 dígitos en total)
    return codigoBanco + numeroCuentaLocal + digitoVerificacion;
  }

  // Método para calcular los dígitos de verificación usando módulo 97
  private static String calcularDigitoVerificadorCCI(String numeroBase) {
    long numero = Long.parseLong(numeroBase) % 97; // Resto de la división por 97
    int digitoVerificacion = (int) (98 - numero);  // Fórmula estándar para CCI

    return String.format("%02d", digitoVerificacion); // Retorna en formato de 2 dígitos
  }

  public static void main(String[] args) {
// Paso 1: Generar un número de cuenta bancaria local
    String numeroCuentaBancaria = numberAccountGenerate();
    System.out.println("Número de Cuenta Bancaria Local generado: " + numeroCuentaBancaria);

    // Paso 2: Definir un código de banco (ejemplo: "001")
    String codigoBanco = "001";  // Ejemplo de código de banco

    // Paso 3: Generar el número de cuenta interbancario (CCI) con el número de cuenta y el código de banco
    String numeroCCI = generarNumeroCCI(numeroCuentaBancaria, codigoBanco);
    System.out.println("Número de Cuenta Interbancario (CCI) generado: " + numeroCCI);
  }
}

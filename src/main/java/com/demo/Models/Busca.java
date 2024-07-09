package com.demo.Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Busca {
  public static ArrayList<String> usuario(String email) {
    final String PATH_USUARIOS = "src/main/java/com/demo/Database/usuarios.csv";
    ArrayList<String> resultado = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(PATH_USUARIOS))) {
      String linha;
      while ((linha = reader.readLine()) != null) {
        String[] campos = linha.split(",");
        if (campos[0].equals(email) || email.equals("BUSCACOMPLETA")) {
          resultado.addAll(Arrays.asList(campos));
        }
      }
      if(resultado.size() > 0) {
        return resultado;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static ArrayList<String> pergunta(String nivel, String email) {
    final String PATH_PERGUNTAS = "src/main/java/com/demo/Database/nivel" + nivel + ".csv";
    ArrayList<String> resultado = new ArrayList<>();
    try {
      ArrayList<String> performanceList = Busca.performance(email);
      BufferedReader reader = new BufferedReader(new FileReader(PATH_PERGUNTAS));
      String linha;
      String[] respondidas = performanceList.getLast().substring(1, performanceList.getLast().length() - 1).split(";");
      int codigo = randomnumero(nivel, respondidas, PATH_PERGUNTAS);
      while ((linha = reader.readLine()) != null) {
        String[] campos = linha.split(";");
        String idPergunta = campos[campos.length - 1];
        if ((!Arrays.asList(respondidas).contains(idPergunta)) && Integer.parseInt(campos[campos.length - 1]) == codigo) {
          resultado.addAll(Arrays.asList(campos));
          return resultado;
        }
      }
      if (resultado.size() == 0) {
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return resultado;
  }

  public static int tamanhoArquivo(String PATH) {
    int tamanho = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
      String linha;
      while ((linha = reader.readLine()) != null) {
        tamanho++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return tamanho;
  }

  public static ArrayList<String> performance(String email) {
    final String PATH_PERFORMANCE = "src/main/java/com/demo/Database/performance.csv";
    ArrayList<String> resultado = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(PATH_PERFORMANCE))) {
      String linha;
      while ((linha = reader.readLine()) != null) {
        String[] campos = linha.split(",");
        if (campos.length >= 2 && campos[0].equals(email)) {
          resultado.addAll(Arrays.asList(campos));
          return resultado;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    try (FileWriter writer = new FileWriter(PATH_PERFORMANCE, true)) {
      String[] r = {email, "0", "0", "[]"};
      resultado.addAll(Arrays.asList(r));
      writer.write(email + ",0,0,[]\n");
      return resultado;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static int randomnumero(String nivel, String[] respondidas, String PATH) {
    int codigo = 0;
    Arrays.sort(respondidas, (a, b) -> Integer.parseInt(a) - Integer.parseInt(b));
    try {
      Random random = new Random();
      int tamanho = tamanhoArquivo(PATH);
      if (respondidas.length == 0) {
        codigo = 1 + random.nextInt(tamanho);
      } else {
        codigo = 1 + random.nextInt(tamanho - respondidas.length);
        for (String r : respondidas) {
          if (codigo < Integer.parseInt(r)) {
            break;
          }
          codigo++;
        }
      }
      return codigo;
    } catch (Exception e) {
      return codigo;
    }
  }
}
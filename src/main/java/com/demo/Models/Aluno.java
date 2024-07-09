package com.demo.Models;

public class Aluno {
  private String nivel;
  private String email;
  private String senha;

  public Aluno(String email, String senha, String nivel) {
    this.email = email;
    this.senha = senha;
    this.nivel = nivel;
  }

  public String getEmail() {
    return email;
  }

  public String getSenha(){
    return senha;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getNivel(){
    return nivel;
  }

  public void setNivel(String nivel) {
    this.nivel = nivel;
  }
}
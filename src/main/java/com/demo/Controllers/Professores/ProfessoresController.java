package com.demo.Controllers.Professores;

import com.demo.Models.Busca;
import com.demo.Managers.GerenciadorDeView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ProfessoresController {
  @FXML
  public Text bv;
  public Button criapergunta;
  public Button criausuario;
  public Button btnSair;
  public Button alterausuario;

  public void initialize(String email) {
    alterausuario.setOnAction(event -> alteradorUsuario());
    criapergunta.setOnAction(event -> criadorPergunta());
    criausuario.setOnAction(event -> criadorUsuario());
    btnSair.setOnAction(event -> retornarLogin());
    ArrayList<String> array = Busca.usuario(email);
    String nomeCompleto = array.get(2);
    String[] partesNome = nomeCompleto.split(" ");
    String nome = partesNome[0];
    bv.setText("Bem Vindo, " + nome);
  }

  private void criadorPergunta() {
    Stage currentStage = (Stage) criapergunta.getScene().getWindow();
    currentStage.close();
    GerenciadorDeView.getInstance().getView().showCriaPerguntaWindow();
  }

  private void criadorUsuario() {
    Stage currentStage = (Stage) criausuario.getScene().getWindow();
    currentStage.close();
    GerenciadorDeView.getInstance().getView().showCriaUsuarioWindow();
  }

  private void retornarLogin() {
    Stage currentStage = (Stage) btnSair.getScene().getWindow();
    currentStage.close();
    GerenciadorDeView.getInstance().getView().showLoginWindow();
  }

  private void alteradorUsuario(){
    Stage currentStage = (Stage) alterausuario.getScene().getWindow();
    currentStage.close();
    GerenciadorDeView.getInstance().getView().showAlteraUsuarioWindow();
  }

}

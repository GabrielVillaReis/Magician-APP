package com.demo.Controllers;

import com.demo.Support.SingletonUsuario;
import com.demo.Support.SingletonView;
import com.demo.Models.*;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
  public Text mensagemresposta;
  public TextField email;
  public PasswordField senha;
  public Button loginbtn;

  public void initialize() {
    loginbtn.setOnAction(actionEvent -> onLogin());
    if (Busca.usuario("BUSCACOMPLETA").size() <=4){
      mensagemresposta.setText("Usuario inicial -> login: admin@com senha: 123");
      mensagemresposta.setFill(Color.BLACK);
    }
  }

  private void onLogin() {
    String semail = email.getText().toLowerCase().trim();
    String ssenha = senha.getText();
    String[] niveltipo = Usuario.autentica(semail, ssenha);
    if (niveltipo == null) {
      mensagemresposta.setText("Erro: Verifique se os campos foram digitados corretamente");
      mensagemresposta.setFill(Color.RED);
      return;
    }

    Stage currentStage = (Stage) loginbtn.getScene().getWindow();
    currentStage.close();

    if ("a".equals(niveltipo[1])) {
      Aluno alunoLogado = new Aluno(semail, ssenha, niveltipo[0]);
      SingletonUsuario.getInstancia().setAlunoLogado(alunoLogado);
      SingletonView.getInstance().getView().showAlunoMenuWindow();
    } else {
      Professor professorLogado = new Professor(semail, ssenha);
      SingletonUsuario.getInstancia().setProfessorLogado(professorLogado);
      SingletonView.getInstance().getView().showProfessorWindow();
    }
  }
}
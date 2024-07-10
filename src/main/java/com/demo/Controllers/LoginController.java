package com.demo.Controllers;

import com.demo.Support.SingletonUsuario;
import com.demo.Support.SingletonView;
import com.demo.Models.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController  {
  public Text errormessage;
  public TextField email;
  public PasswordField senha;
  public Button loginbtn;

  
  public void initialize() {
    loginbtn.setOnAction(actionEvent -> onLogin());
  }

  private void onLogin() {
    String semail = email.getText().toLowerCase().trim();
    String ssenha = senha.getText();
    String[] niveltipo = Usuario.autentica(semail, ssenha);
    if (niveltipo == null) {
      errormessage.setText("Erro: Verifique se os campos foram digitados corretamente");
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
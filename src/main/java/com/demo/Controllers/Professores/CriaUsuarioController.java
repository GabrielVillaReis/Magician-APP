package com.demo.Controllers.Professores;

import com.demo.Models.Busca;
import com.demo.Models.Usuario;
import com.demo.Support.SingletonView;
import com.demo.Models.Performance;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CriaUsuarioController {
  final String PATH_USUARIOS = "src/main/java/com/demo/Database/usuarios.csv";

  @FXML
  public TextField uemail;
  public TextField senha;
  public TextField nome;
  ;
  public Button criar;
  public Button voltar;
  public ChoiceBox<String> tipo;
  public ChoiceBox<String> nivel;
  public Text mensagemresposta;
  public Text nivelText;
  public Text tipoText;
  private String atipo = "";
  private String anivel;

  public void initialize() {
    nivel.getItems().addAll("Aprendiz", "Feiticeiro", "Bruxo", "Mago");
    tipo.getItems().addAll("Aluno", "Professor");
    nivelText.setMouseTransparent(true);
    tipoText.setMouseTransparent(true);
    nivel.setVisible(false);
    nivelText.setVisible(false);
    tipo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if ("Aluno".equals(newValue)) {
          nivel.setVisible(true);
          nivelText.setVisible(true);
        } else {
          nivel.setVisible(false);
          nivelText.setVisible(false);
        }
      }
    });
    nivel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        if (t1 != null) {
          nivelText.setVisible(false);
        }
      }
    });
    tipo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        if (t1 != null) {
          tipoText.setVisible(false);
        }
      }
    });
    criar.setOnAction(event -> criaUsuario());
    voltar.setOnAction(event -> voltarTela());
  }

  private void criaUsuario() {
    if (tipo.getValue() == "Aluno" && nivel.getValue() != null) {
      atipo = "a";
      anivel = Performance.numNivel(nivel.getValue());
    } else if (tipo.getValue() == "Professor")
      atipo = "p";
    if (atipo.equals("a") && (uemail.getText().isEmpty() || nivel.getValue() == null || senha.getText().isEmpty() || nome.getText().isEmpty() || atipo.isEmpty())) {
      mensagemresposta.setFill(Color.RED);
      mensagemresposta.setText("Todos os campos devem ser preenchidos");
      return;
    }

    if (atipo.equals("p") && (uemail.getText().isEmpty() || senha.getText().isEmpty() || nome.getText().isEmpty() || atipo.isEmpty())) {
      mensagemresposta.setFill(Color.RED);
      mensagemresposta.setText("Todos os campos devem ser preenchidos");
      return;
    }

    if (atipo.isEmpty()) {
      mensagemresposta.setFill(Color.RED);
      mensagemresposta.setText("Todos os campos devem ser preenchidos");
      return;
    }

    if(uemail.getText().length() < 2 || senha.getText().length() < 2 || nome.getText().length() < 2){
      mensagemresposta.setFill(Color.RED);
      mensagemresposta.setText("O email senha e nome precisam ter ao menos 3 caracters");
      return;
    }

    if (Busca.usuario(uemail.getText()) != null) {
      mensagemresposta.setFill(Color.RED);
      mensagemresposta.setText("Usuário já cadastrado");
      return;
    }

    try {

      if (atipo.equals("a")) {
        String dados = String.format("%s,%s,%s,%s,%s\n",
                uemail.getText().toLowerCase(),
                senha.getText(),
                nome.getText(),
                anivel,
                atipo);
        Usuario.adiciona(dados);
        mensagemresposta.setFill(Color.BLACK);
        mensagemresposta.setText("Usuário cadastrado com sucesso");
        limparCampos();
      } else if (atipo.equals("p")) {
        String dados = String.format("%s,%s,%s,%s\n",
                uemail.getText().toLowerCase(),
                senha.getText(),
                nome.getText(),
                atipo);
        Usuario.adiciona(dados);
        mensagemresposta.setFill(Color.BLACK);
        mensagemresposta.setText("Usuário cadastrado com sucesso");
        limparCampos();
      }

    } catch (Exception e) {
      mensagemresposta.setFill(Color.RED);
      mensagemresposta.setText("Erro ao salvar o usuário");
      e.printStackTrace();
    }
  }


  private void limparCampos() {
    uemail.clear();
    senha.clear();
    nome.clear();
    tipo.setValue(null);
    nivel.setValue(null);
    anivel = "";
    atipo = "";
    tipoText.setVisible(true);
    nivelText.setVisible(false);
  }

  private void voltarTela() {
    Stage stage = (Stage) voltar.getScene().getWindow();
    stage.close();
    SingletonView.getInstance().getView().showProfessorWindow();
  }
}


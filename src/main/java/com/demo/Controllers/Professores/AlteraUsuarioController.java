package com.demo.Controllers.Professores;

import com.demo.Models.Busca;
import com.demo.Models.Usuario;
import com.demo.Support.SingletonView;
import com.demo.Models.Performance;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class AlteraUsuarioController {
  public Button voltar;
  public Button alterar;
  public Button buscar;
  public Button subirNivel;
  public Button descerNivel;
  public Button deletar;
  public TextField emailText;
  public TextField nomeText;
  public TextField senhaText;
  public Text mensagemresposta;
  public Label nivelText;
  public Label performanceText;
  public Label nivelTitle;
  public Label performanceTitle;
  private ArrayList<String> usuario;
  private ArrayList<String> aperformance;
  private String uemail;
  private String usenha;
  private String unome;
  private String anivel = "";
  private String admemail;

  public void initialize(String email) {
    admemail = email;
    subirNivel.setVisible(false);
    descerNivel.setVisible(false);
    alterar.setVisible(false);
    nomeText.setVisible(false);
    senhaText.setVisible(false);
    nivelText.setVisible(false);
    performanceText.setVisible(false);
    nivelTitle.setVisible(false);
    performanceTitle.setVisible(false);
    deletar.setVisible(false);
    voltar.setOnAction(event -> voltarParaProfessores());
    buscar.setOnAction(event -> buscarUsuario());
    alterar.setOnAction(event -> alterarUsuario());
    subirNivel.setOnAction(event -> sobeNivel());
    descerNivel.setOnAction(event -> desceNivel());
    deletar.setOnAction(event -> deletarUsuario());
  }

  private void voltarParaProfessores() {
    Stage stage = (Stage) voltar.getScene().getWindow();
    stage.close();
    SingletonView.getInstance().getView().showProfessorWindow();
  }

  private void buscarUsuario() {
    usuario = Busca.usuario(emailText.getText().toLowerCase().trim());
    if (usuario == null) {
      mensagemresposta.setFill(Color.RED);
      mensagemresposta.setVisible(true);
      mensagemresposta.setText("Usuário não encontrado");
      limparCampos();
    } else {
      mensagemresposta.setVisible(false);
      uemail = emailText.getText().toLowerCase().trim();
      aperformance = Busca.performance(uemail);
      if (usuario.getLast().equals("a")) {
        mostraAluno();
      } else {
        mostraProfessor();
      }
    }
  }

  private void deletarUsuario() {
    Performance.salva("", uemail);
    ArrayList<String> linhas = Usuario.retornaArquivo(usuario, "0");
    Usuario.altera(linhas);
    usuario = null;
    limparCampos();
    mensagemresposta.setVisible(true);
    mensagemresposta.setText("Usuário Apagado!");
  }

  private void alterarUsuario() {
    ArrayList<String> linhas;
    usenha = senhaText.getText();
    unome = nomeText.getText();
    usuario.clear();
    if (!anivel.isEmpty()) {
      usuario.addAll(Arrays.asList(uemail, usenha, unome, anivel, "a"));
    } else usuario.addAll(Arrays.asList(uemail, usenha, unome, "p"));
    linhas = Usuario.retornaArquivo(usuario, anivel);
    Usuario.altera(linhas);
    mensagemresposta.setVisible(true);
    mensagemresposta.setFill(Color.BLACK);
    mensagemresposta.setText("Alteração realizada com sucesso!");
    usuario = Busca.usuario(uemail);
  }

  private void sobeNivel() {
    String novaperformace = uemail + ",0,0,[]";
    anivel = String.valueOf(Integer.parseInt(anivel) + 1);
    Performance.salva(novaperformace, uemail);
    ArrayList<String> linhas = Usuario.retornaArquivo(usuario, anivel);
    Usuario.altera(linhas);
    aperformance.clear();
    aperformance.addAll(Arrays.asList(novaperformace.split(",")));
    usuario.set(3, anivel);
    mostraAluno();
  }

  private void desceNivel() {
    String novaperformace = uemail + ",0,0,[]";
    anivel = String.valueOf(Integer.parseInt(anivel) - 1);
    Performance.salva(novaperformace, uemail);
    ArrayList<String> linhas = Usuario.retornaArquivo(usuario, anivel);
    Usuario.altera(linhas);
    aperformance.clear();
    aperformance.addAll(Arrays.asList(novaperformace.split(",")));
    usuario.set(3, anivel);
    mostraAluno();
  }

  private void mostraAluno() {
    nomeText.setText(usuario.get(2));
    senhaText.setText(usuario.get(1));
    anivel = usuario.get(3);
    nomeText.setVisible(true);
    senhaText.setVisible(true);
    performanceText.setVisible(true);
    nivelText.setVisible(true);
    performanceTitle.setVisible(true);
    nivelTitle.setVisible(true);
    alterar.setVisible(true);
    deletar.setVisible(true);
    nivelText.setText(Performance.showNivel(uemail));
    if (Integer.parseInt(anivel) < 4)
      subirNivel.setVisible(true);
    else subirNivel.setVisible(false);
    if (Integer.parseInt(anivel) > 1)
      descerNivel.setVisible(true);
    else descerNivel.setVisible(false);
    double numAcertos = Double.parseDouble(aperformance.get(1));
    double numRespostas = Double.parseDouble(aperformance.get(2));
    double porcento;
    if (numRespostas != 0) {
      porcento = numAcertos / numRespostas;
    } else {
      porcento = numRespostas;
    }
    performanceText.setText("Questões respondidas: " +
            (int) numAcertos +
            "\nAcertos: " +
            (int) numRespostas +
            "\n(" +
            String.format("%.1f", porcento * 100) +
            "%)");
  }

  private void mostraProfessor() {
    anivel = "";
    nomeText.setText(usuario.get(2));
    senhaText.setText(usuario.get(1));
    nomeText.setVisible(true);
    senhaText.setVisible(true);
    alterar.setVisible(true);
    subirNivel.setVisible(false);
    descerNivel.setVisible(false);
    performanceText.setVisible(false);
    nivelText.setVisible(false);
    performanceTitle.setVisible(false);
    nivelTitle.setVisible(false);
    if (emailText.getText().equals(admemail))
      deletar.setVisible(false);
    else deletar.setVisible(true);
  }

  private void limparCampos() {
    emailText.setText("");
    nomeText.setVisible(false);
    senhaText.setVisible(false);
    alterar.setVisible(false);
    subirNivel.setVisible(false);
    descerNivel.setVisible(false);
    deletar.setVisible(false);
    performanceText.setVisible(false);
    nivelText.setVisible(false);
    performanceTitle.setVisible(false);
    nivelTitle.setVisible(false);
  }

}

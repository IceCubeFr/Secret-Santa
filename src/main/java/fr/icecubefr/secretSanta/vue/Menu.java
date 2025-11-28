package fr.icecubefr.secretSanta.vue;

import fr.icecubefr.secretSanta.modele.SecretManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class Menu extends Stage {
    SecretManager secretManager;
    public Menu() {
        this.secretManager = new SecretManager();
        this.setScene(participantEntry());
        this.setFullScreen(true);
        this.show();
    }

    public Scene participantEntry() {
        VBox conteneur = new VBox();
        Label title = new Label("Secret Santa");
        title.setFont(new Font(32));
        Label desc = new Label("Entrez le nom de tous les participants au programme de SecretSanta");
        desc.setFont(new Font(24));
        conteneur.setPadding(new Insets(50));
        conteneur.setSpacing(50);
        ListView<HBox> listView = new ListView<>();
        updateList(listView, secretManager.getSantas());
        HBox newEntry = new HBox();
        TextField textField = new TextField();
        textField.setPromptText("Veuillez entrer un nom");
        textField.setPadding(new Insets(20));
        textField.setOnAction(event -> {
            secretManager.addParticipant(textField.getText());
            textField.clear();
            updateList(listView, secretManager.getSantas());
        });
        Button button = new Button("Ajouter");
        button.setOnAction(event -> {
            secretManager.addParticipant(textField.getText());
            textField.clear();
            updateList(listView, secretManager.getSantas());
        });
        HBox.setHgrow(textField, Priority.ALWAYS);
        button.setPadding(new Insets(20));
        newEntry.setSpacing(10);
        newEntry.getChildren().addAll(textField, button);

        Button launcher = new Button("Générer les duos");
        HBox.setHgrow(launcher, Priority.ALWAYS);
        launcher.setOnAction(event -> {
            Map<String, String> duos = secretManager.launchSecretSanta();
            this.setScene(santaResults(duos));
            this.setFullScreen(true);
        });

        conteneur.getChildren().addAll(title, desc, listView, newEntry, launcher);
        conteneur.setAlignment(Pos.CENTER);
        return new Scene(conteneur);
    }

    private void updateList(ListView<HBox> listView, List<String> parts) {
        listView.getItems().clear();
        for(String part : parts) {
            HBox hbox = new HBox();
            Label label = new Label(part);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            Button button = new Button("Supprimer");
            button.setOnAction(event -> {
                secretManager.removeParticipant(part);
                updateList(listView, secretManager.getSantas());
            });
            hbox.getChildren().addAll(label, spacer, button);
            listView.getItems().add(hbox);
        }
    }

    public Scene santaResults(Map<String, String> duos) {
        VBox conteneur = new VBox();
        Label title = new Label("Résultats");
        title.setFont(new Font(32));
        ListView<HBox> listView = new ListView<>();
        for(Map.Entry<String, String> entry : duos.entrySet()) {
            HBox hbox = new HBox();
            Label label = new Label(entry.getKey());
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            Label label2 = new Label(entry.getValue());
            hbox.getChildren().addAll(label, spacer, label2);
            listView.getItems().add(hbox);
        }
        Button retour = new Button("Retour");
        retour.setOnAction(event -> {
            this.setScene(participantEntry());
            this.setFullScreen(true);
        });
        retour.setPadding(new Insets(20));
        HBox.setHgrow(retour, Priority.ALWAYS);
        conteneur.setAlignment(Pos.CENTER);
        conteneur.setPadding(new Insets(50));
        conteneur.setSpacing(50);
        conteneur.getChildren().addAll(title, listView, retour);
        return new Scene(conteneur);
    }
}

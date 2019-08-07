package eu.theritual.wrathofbahrott.viewoperator;

import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.viewoperator.viewutils.ViewUtils;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import org.springframework.stereotype.Controller;

@Controller
public class MainMenuController {
    @FXML
    BorderPane menuPane;

    @FXML
    MediaView musicPlayer;
    private DataOperator dataOperator;

    @FXML
    private void exitAction(Event e) {
        Platform.exit();
    }

    private void buttonHoverImage(Event e) {
        ImageView img = (ImageView) e.getSource();
        img.setImage(ViewUtils.getImage(img.getId() + "On", 263, 100));
    }

    private void buttonUnHoverImage(Event e) {
        ImageView img = (ImageView) e.getSource();
        img.setImage(ViewUtils.getImage(img.getId() + "Out", 263, 100));
    }

    void setDataOperator(DataOperator dataOperator) {
        this.dataOperator = dataOperator;
    }

    private ImageView createMenuButton(String gfxName) {
        ImageView btn = ViewUtils.getImageView(gfxName + "Out", 263, 100);
        btn.setId(gfxName);
        btn.setOnMouseEntered(this::buttonHoverImage);
        btn.setOnMouseExited(this::buttonUnHoverImage);
        return btn;
    }

    void startMenu() {
        menuPane.setPrefSize(dataOperator.getViewOperator().getScreenWidth(), dataOperator.getViewOperator().getScreenHeight());
        menuPane.setBackground(ViewUtils.fullWindowBG("menuBackground", dataOperator.getViewOperator().getScreenWidth(), dataOperator.getViewOperator().getScreenHeight()));
        ImageView wobLogo = ViewUtils.getImageView("wobLogo", dataOperator.getViewOperator().getScreenWidth() * 0.50, dataOperator.getViewOperator().getScreenHeight() * 0.50);
        menuPane.setTop(wobLogo);
        BorderPane.setAlignment(wobLogo, Pos.TOP_CENTER);
        VBox menuList = new VBox();
        menuList.setAlignment(Pos.TOP_CENTER);
        ImageView exitButton = createMenuButton("exit");
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::exitAction);
        ImageView startButton = createMenuButton("start");
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> System.out.println("Startuję"));
        menuList.getChildren().add(startButton);
        menuList.getChildren().add(exitButton);
        menuPane.setCenter(menuList);
        BorderPane.setAlignment(menuList, Pos.TOP_CENTER);
        musicPlayer.setMediaPlayer(dataOperator.getMediaOperator().getMediaPlayerWithMusic("menuMusic", Timeline.INDEFINITE));
        musicPlayer.getMediaPlayer().play();
    }
}

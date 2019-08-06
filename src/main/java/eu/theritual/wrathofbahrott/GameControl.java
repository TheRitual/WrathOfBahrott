package eu.theritual.wrathofbahrott;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.theritual.wrathofbahrott.dataoperator.DataOperator;
import eu.theritual.wrathofbahrott.dataoperator.GameModule;
import eu.theritual.wrathofbahrott.soundoperator.SoundOperator;
import eu.theritual.wrathofbahrott.viewoperator.ViewOperator;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

class GameControl {
    final private ViewOperator viewOperator;
    private DataOperator dataOperator;
    private SoundOperator soundOperator;

    GameControl(Stage mainStage, ConfigurableApplicationContext context) {
        dataOperator = new DataOperator();
        viewOperator = new ViewOperator(mainStage , dataOperator);
        soundOperator = new SoundOperator(dataOperator);
        dataOperator.setModule(GameModule.SPLASH_SCREEN);
        dataOperator.setSpringContext(context);
        dataOperator.setSoundOperator(soundOperator);
        dataOperator.setViewOperator(viewOperator);
        start();

        try {
            String test = new ObjectMapper().writeValueAsString(dataOperator);
            System.out.println(test);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    void start() {
        viewOperator.setUpBeforeRun(true, true);
        viewOperator.run(dataOperator.getModule());
    }
}
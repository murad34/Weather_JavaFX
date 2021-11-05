package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city_textfield;

    @FXML
    private Label davleniye_label;

    @FXML
    private TextField davleniye_textfield;

    @FXML
    private Button ok_button;

    @FXML
    private Label temperature_label;

    @FXML
    private TextField temperature_textfield;

    @FXML
    private Label vlajnost_label;

    @FXML
    private TextField vlajnost_textfield;

    @FXML
    private Label weather_label;

    @FXML
    private ImageView imageView;

    @FXML
    private Label citynotfound_label;

    //-------------------------------------------

    @FXML
    void ok_button_action(ActionEvent event) {

        String getUserCity = city_textfield.getText().trim();

        if (!getUserCity.equals("")) {

            String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&units=metric&appid=7452ca43b0d9fe4af566d6d54a42de3a");

            System.out.println(output);

            if (!output.isEmpty()) {
                JSONObject obj = new JSONObject(output);
                temperature_textfield.setText(String.valueOf(obj.getJSONObject("main").getDouble("temp")));
                davleniye_textfield.setText(String.valueOf(obj.getJSONObject("main").getDouble("pressure")));
                vlajnost_textfield.setText(String.valueOf(obj.getJSONObject("main").getDouble("humidity")));

                String imageName = obj.getJSONArray("weather").getJSONObject(0).get("icon").toString();

                Image icon = new Image("http://openweathermap.org/img/w/" + imageName + ".png");
                imageView.setImage(icon);

//              temperature_textfield.setText("Температура : " + obj.getJSONObject("main").getDouble("temp"));

            }

        }

    }

    public String getUrlContent(String urlAddress) {

        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }

            bufferedReader.close();

            imageView.setVisible(true);

            citynotfound_label.setVisible(false);

        } catch (Exception e) {

            System.out.println("Город не найден !");

            imageView.setVisible(false);

            citynotfound_label.setVisible(true);

        }

        return content.toString();
    }

    @FXML
    void initialize() {

        ok_button.setDefaultButton(true);

    }

}

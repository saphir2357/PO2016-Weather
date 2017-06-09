package com.github.saphir2357.po2016.weather.ui;

import com.github.saphir2357.po2016.weather.data.NoDataException;
import com.github.saphir2357.po2016.weather.data.WeatherData;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;



public class MainWindow {
    private static final String NOT_AVAILABLE_TEXT = "NA";
    private static final String CELCIUS = "°C";

    private static final Font HUGE_FONT = new Font(72);
    private static final Font NORMAL_FONT = new Font(22);
    private static final Font SMALL_FONT = new Font(14);

    private static final Image WIND_ROSE_IMAGE = new Image("Rose.png");
    private static final Image WIND_ARROW_IMAGE = new Image("Arrow.png");

    private GridPane mainPane = new GridPane();
    private Scene scene;

    private ImageView weatherIcon = new ImageView();
    private Label temperatureLabel = new Label();
    private ImageView windArrow = new ImageView();
    private Label windLabel = new Label();
    private Label humidityLabel = new Label();
    private Label pressureLabel = new Label();
    private Label aqiLabel = new Label();
    private Label pm25Label = new Label();
    private Label pm10Label = new Label();
    private Label rainLabel = new Label();
    private Label cloudinessLabel = new Label();

    private WeatherData dataSource;


    public MainWindow(WeatherData dataSource) {
        setupScene();
        this.dataSource = dataSource;
        refresh();
    }


    private void setupScene() {
        mainPane.setHgap(15);
        mainPane.setVgap(15);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHalignment(HPos.CENTER);
        cc.setPercentWidth(33);
        RowConstraints rc = new RowConstraints();
        rc.setValignment(VPos.TOP);
        mainPane.getColumnConstraints().addAll(cc, cc, cc);
        mainPane.getRowConstraints().addAll(rc, rc, rc);

        addLocationLabel(0);
        addTemperatureAndImage(1);
        addWindCells(0, 2);
        addPressureCell(2, 2);
        addHumidityCell(0, 3);
        addRainVolumeCell(1, 3);
        addCloudinessCell(2, 3);
        addAQRow(4);

        StackPane mainLayoutWrapper;
        mainLayoutWrapper = new StackPane(mainPane);
        mainLayoutWrapper.setPrefSize(600, 250);
        mainPane.setPadding(new Insets(20, 40, 20, 40));
        scene = new Scene(mainLayoutWrapper);
    }


    private void addLocationLabel(int gridY) {
        Label ll = new Label("Warsaw, Poland");
        ll.setFont(NORMAL_FONT);
        mainPane.add(ll, 0, gridY, 3, 1);
    }


    private void addTemperatureAndImage(int gridY) {
        weatherIcon.setFitHeight(96);
        weatherIcon.setFitWidth(96);
        mainPane.add(weatherIcon, 0, gridY);
        mainPane.add(temperatureLabel, gridY, 1, 2, 1);
        temperatureLabel.setFont(HUGE_FONT);
    }


    private void addWindCells(int gridX, int gridY) {
        windArrow.setFitWidth(28);
        windArrow.setFitHeight(28);
        windArrow.setRotate(90);
        windLabel.setFont(NORMAL_FONT);
        addLabeledCell(gridX, gridY, "Wind speed", windLabel);
        addLabeledCell(gridX + 1, gridY, "Wind direction", windArrow);
    }


    private void addHumidityCell(int gridX, int gridY) {
        humidityLabel.setFont(NORMAL_FONT);
        addLabeledCell(gridX, gridY, "Humidity", humidityLabel);
    }


    private void addPressureCell(int gridX, int gridY) {
        pressureLabel.setFont(NORMAL_FONT);
        addLabeledCell(gridX, gridY, "Pressure", pressureLabel);
    }


    private void addAQRow(int gridY) {
        aqiLabel.setFont(NORMAL_FONT);
        addLabeledCell(0, gridY, "Air Quality Index", aqiLabel);
        pm25Label.setFont(NORMAL_FONT);
        addLabeledCell(1, gridY, "PM2.5 Particles", pm25Label);
        pm10Label.setFont(NORMAL_FONT);
        addLabeledCell(2, gridY, "PM10 Particles", pm10Label);
    }


    private void addRainVolumeCell(int gridX, int gridY) {
        rainLabel.setFont(NORMAL_FONT);
        addLabeledCell(gridX, gridY, "Rain Volumne in 3h", rainLabel);
    }


    private void addCloudinessCell(int gridX, int gridY) {
        cloudinessLabel.setFont(NORMAL_FONT);
        addLabeledCell(gridX, gridY, "Cloudiness", cloudinessLabel);
    }


    private void addLabeledCell(int gridX, int gridY, String label, Node content) {
        Label lbl = new Label(label);
        lbl.setFont(SMALL_FONT);
        VBox vbox = new VBox(lbl, content);
        vbox.setAlignment(Pos.CENTER);
        mainPane.add(vbox, gridX, gridY);
    }


    public Scene getScene() {
        return scene;
    }


    public void refresh() {
        weatherIcon.setImage(new Image(dataSource.weatherImageName()));

        try {
            temperatureLabel.setText(dataSource.celcius() + CELCIUS);
        } catch (NoDataException e) {
            temperatureLabel.setText(NOT_AVAILABLE_TEXT);
        }

        try {
            windLabel.setText(dataSource.windSpeedKmh() + " km/h");
            windArrow.setImage(WIND_ARROW_IMAGE);
            windArrow.setRotate(dataSource.windDirectionDegree());
        } catch (NoDataException e) {
            windLabel.setText(NOT_AVAILABLE_TEXT);
            windArrow.setRotate(0);
            windArrow.setImage(WIND_ROSE_IMAGE);
        }

        try {
            humidityLabel.setText(dataSource.humidityPercent() + "%");
        } catch (NoDataException e) {
            humidityLabel.setText(NOT_AVAILABLE_TEXT);
        }

        try {
            pressureLabel.setText(dataSource.hPaPressure() + " hPa");
        } catch (NoDataException e) {
            pressureLabel.setText(NOT_AVAILABLE_TEXT);
        }

        try {
            aqiLabel.setText(Double.toString(dataSource.airQualityIndex()));
        } catch (NoDataException e) {
            aqiLabel.setText(NOT_AVAILABLE_TEXT);
        }

        try {
            pm25Label.setText(Double.toString(dataSource.pm25()));
        } catch (NoDataException e) {
            pm25Label.setText(NOT_AVAILABLE_TEXT);
        }

        try {
            pm10Label.setText(Double.toString(dataSource.pm10()));
        } catch (NoDataException e) {
            pm10Label.setText(NOT_AVAILABLE_TEXT);
        }

        try {
            rainLabel.setText(Double.toString(dataSource.rainVolume3h()) + "mm");
        } catch (NoDataException e) {
            rainLabel.setText(NOT_AVAILABLE_TEXT);
        }

        try {
            cloudinessLabel.setText(Double.toString(dataSource.cloudinessPercent()) + "%");
        } catch (NoDataException e) {
            cloudinessLabel.setText(NOT_AVAILABLE_TEXT);
        }
    }
}

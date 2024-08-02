package com.leshkins.cronnews.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NewsController {

    @FXML
    private ListView<String> newsListView;

    @FXML
    private Label headlineLabel;

    @FXML
    private Text descriptionText;

//    @FXML
//    private ImageView photoImageView;


    private final NewsService newsService = new NewsService();
    private List<NewsDTO> newsList;
    private int currentIndex = 0;

    @FXML
    public void initialize() {
        newsList = newsService.getTodayNews();
        updateNewsListView();

        newsListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if ((int) newValue >= 0 && (int) newValue < newsList.size()) {
                showNewsDetails(newsList.get((int) newValue));
                currentIndex = (int) newValue;
            }
        });
    }

    private void showNewsDetails(NewsDTO news) {
        headlineLabel.setText(news.headline());
        descriptionText.setText(news.description());

//        try {
//            URL mediaUrl = new URL(news.mediaUrl());
//            Image image = new Image(mediaUrl.toString(), true);
//
//            photoImageView.setImage(image);
//
//            System.out.println(image.isError());
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }

    @FXML
    private void handlePrevious(ActionEvent event) {
        if (currentIndex > 0) {
            currentIndex--;
            newsListView.getSelectionModel().select(currentIndex);
        }
    }

    @FXML
    private void handleNext(ActionEvent event) {
        if (currentIndex < newsList.size() - 1) {
            currentIndex++;
            newsListView.getSelectionModel().select(currentIndex);
        }
    }

    @FXML
    private void handleShowMorningNews(ActionEvent event){
        this.newsList = newsService.getTodayMorningNews();
        updateNewsListView();

    }
    @FXML
    private void handleShowMiddayNews(ActionEvent event){
        this.newsList = newsService.getTodayMiddayNews();
        updateNewsListView();
    }
    @FXML
    private void handleShowEveningNews(ActionEvent event){
        this.newsList = newsService.getTodayEveningNews();
        updateNewsListView();

    }

    private void updateNewsListView() {
        // Create a new ObservableList for headlines
        ObservableList<String> headlines = FXCollections.observableArrayList();

        // Add headlines from newsList to the ObservableList
        for (NewsDTO news : newsList) {
            headlines.add(news.headline());
        }

        // Set the ObservableList to the ListView
        newsListView.setItems(headlines);

        // Check if newsList is not empty and update the currentIndex
        if (!newsList.isEmpty()) {
            currentIndex = 0; // Reset to the first item
            newsListView.getSelectionModel().select(currentIndex); // Select the first item
            showNewsDetails(newsList.get(currentIndex)); // Show details of the selected news item
        } else {
            // Clear the details if the newsList is empty
            headlineLabel.setText("");
            descriptionText.setText("");
        }
    }

}

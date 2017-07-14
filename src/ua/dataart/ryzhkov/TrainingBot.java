package ua.dataart.ryzhkov;

import com.google.maps.errors.ApiException;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.generics.BotOptions;
import tk.plogitech.darksky.forecast.ForecastException;
import ua.dataart.ryzhkov.commands.WeatherCommand;

import java.io.IOException;
import java.io.Serializable;


public class TrainingBot extends TelegramLongPollingBot {

	private final String weatherAPIKey = BuildVars.darkSkyApiKey ;


	public void onUpdateReceived(Update update) {
		// We check if the update has a message and the message has text
		Update currentUpdate = update;



		if(currentUpdate.getMessage().getText().contains("!weather")){
			SendMessage message = null;
			try {
				message = new SendMessage()
						.setChatId(update.getMessage().getChatId())
						.setText(WeatherCommand
								.currentWeather(currentUpdate.getMessage().getText().substring(9)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ApiException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ForecastException e) {
				e.printStackTrace();
			}
			try {
				sendMessage(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}

		}
		if (update.hasMessage() && update.getMessage().hasText()) {
			SendMessage message = new SendMessage()
					.setChatId(update.getMessage().getChatId())
					.setText(update.getMessage().getText());
			try {
				sendMessage(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}

		}
	}

	public String getBotUsername() {
		return "TrainingBot";
	}

	public String getBotToken() {
		return BuildVars.telegramApiKey;
	}
}

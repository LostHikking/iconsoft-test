package io.github.losthikking.iconsofttest.util;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class AlertUtils {
	private AlertUtils(){}

	/**
	 * Показывает сообщение об ошибке пользователю
	 * @param alertType тип ошибки
	 * @param owner владелец окна
	 * @param title заголовок
	 * @param message сообщение об ошибке
	 */
	public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
}
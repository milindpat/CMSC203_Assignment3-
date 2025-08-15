

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FXMainPane extends BorderPane {

	private Button decryption, exitButton, encryption, test, clearButton;
	private TextField plainTextTextField, inputForEncryptionTextField, encryptedStringTextField3, decryptedTextField4;
	private Label plainTextLabel, descriptionForInputLabel, encryptedLabel3, decryptedLabel4, blankLabel1, blankLabel2;
	private RadioButton radioButton1, radioButton2;
	private int shiftInt = 0;
	private final String BLANK = "                 ";

	private void buildTextFields() {
		plainTextTextField = new TextField();
		inputForEncryptionTextField = new TextField();
		encryptedStringTextField3 = new TextField();
		decryptedTextField4 = new TextField();
	}

	private void buildLabels() {
		plainTextLabel = new Label("Enter plain-text string to encrypt");
		descriptionForInputLabel = new Label("Cyber Key - enter a keyword for Vigenere Cipher");
		encryptedLabel3 = new Label("Encrypted string");
		decryptedLabel4 = new Label("Decrypted string");
		buildBlankLabels();
	}

	private void buildBlankLabels() {
		blankLabel1 = new Label(BLANK);
		blankLabel2 = new Label(BLANK);
		setRight(blankLabel1);
		setLeft(blankLabel2);
	}

	private void buildRadioButtons() {
		radioButton1 = new RadioButton("Use Vigenere cipher");
		radioButton2 = new RadioButton("Use Playfair cipher");
		ToggleGroup radioButtonGroup = new ToggleGroup();
		radioButton1.setToggleGroup(radioButtonGroup);
		radioButton2.setToggleGroup(radioButtonGroup);

		radioButton1.setSelected(true);
		radioButton1.setAlignment(Pos.CENTER);
		radioButton2.setAlignment(Pos.CENTER);

		RadioButtonListener radioButtonListener = new RadioButtonListener();
		radioButton1.setOnAction(radioButtonListener);
		radioButton2.setOnAction(radioButtonListener);
	}

	private void buildButtons() {
		exitButton = new Button("E_xit");
		exitButton.setMnemonicParsing(true);
		exitButton.setTooltip(new Tooltip("Select to close the application"));

		clearButton = new Button("_Clear");
		clearButton.setMnemonicParsing(true);
		clearButton.setTooltip(new Tooltip("Select this to clear the text fields"));

		decryption = new Button("_Decrypt a string");
		decryption.setMnemonicParsing(true);
		decryption.setTooltip(new Tooltip("Select this to decrypt a string"));

		encryption = new Button("Encrypt a string");
		encryption.setMnemonicParsing(true);
		encryption.setTooltip(new Tooltip("Encrypt the string in the upper textfield"));
		encryption.setVisible(true);
	}

	private void addActionToExitButton() {
		exitButton.setOnAction(event -> {
			Platform.exit();
			System.exit(0);
		});
	}

	private void addActionToClearButton() {
		clearButton.setOnAction(event -> {
			plainTextTextField.setText("");
			inputForEncryptionTextField.setText("");
			encryptedStringTextField3.setText("");
			decryptedTextField4.setText("");
		});
	}

	private void addActionToEncryptButton() {
		encryption.setOnAction(event -> {
			try {
				String keyword = "";
				String encryptedStr = "";

				if (radioButton1.isSelected()) {
					keyword = inputForEncryptionTextField.getText().toUpperCase();
					encryptedStr = CryptoManager.vigenereEncryption(plainTextTextField.getText().toUpperCase(), keyword);
				} else {
					keyword = inputForEncryptionTextField.getText().toUpperCase();
					inputForEncryptionTextField.setText(keyword);
					encryptedStr = CryptoManager.playfairEncryption(plainTextTextField.getText().toUpperCase(), keyword);
				}

				plainTextTextField.setText(plainTextTextField.getText().toUpperCase());
				if (encryptedStr.equals(""))
					encryptedStringTextField3.setText("encryption failed");
				else
					encryptedStringTextField3.setText(encryptedStr);
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
			}
		});
	}

	private void addActionToDecryptButton() {
		decryption.setOnAction(event -> {
			try {
				String encryptedText = encryptedStringTextField3.getText().toUpperCase();
				String keyword = inputForEncryptionTextField.getText().toUpperCase();
				String decryptedText = "";

				if (radioButton1.isSelected()) {
					decryptedText = CryptoManager.vigenereDecryption(encryptedText, keyword);
				} else {
					decryptedText = CryptoManager.playfairDecryption(encryptedText, keyword);
				}
				decryptedTextField4.setText(decryptedText);
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
			}
		});
	}

	private void addButtonActions() {
		addActionToExitButton();
		addActionToClearButton();
		addActionToEncryptButton();
		addActionToDecryptButton();
	}

	FXMainPane() {
		Insets inset = new Insets(10);
		buildTextFields();
		buildLabels();
		buildRadioButtons();
		buildButtons();
		addButtonActions();

		HBox topBox = new HBox();
		HBox.setMargin(radioButton1, inset);
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().addAll(radioButton1, radioButton2);
		topBox.setStyle("-fx-border-color: gray;");

		VBox centerBox = new VBox(10);
		centerBox.getChildren().addAll(plainTextLabel, plainTextTextField, encryptedLabel3, encryptedStringTextField3,
				decryptedLabel4, decryptedTextField4, descriptionForInputLabel, inputForEncryptionTextField);
		setCenter(centerBox);

		setTop(topBox);

		HBox bottomBox = new HBox();
		HBox.setMargin(decryption, inset);
		HBox.setMargin(encryption, inset);
		HBox.setMargin(clearButton, inset);
		HBox.setMargin(exitButton, inset);
		bottomBox.getChildren().addAll(encryption, decryption, clearButton, exitButton);
		setBottom(bottomBox);
		bottomBox.setAlignment(Pos.CENTER);
	}

	class RadioButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Object source = event.getTarget();
			if (source == radioButton1) {
				descriptionForInputLabel.setText("Cyber Key - enter a keyword for Vigenere Cipher");
			} else if (source == radioButton2) {
				descriptionForInputLabel.setText("Cyber Key - enter a string for Playfair Cipher");
			}
		}
	}
}
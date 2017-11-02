package game;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// this class is for casanova's current position
class PlayBoy {

	int x;
	int y;
}

public class Main extends Application {

	// collection of set lists for file IO
	TreeSet<Casanova> treenames = new TreeSet<Casanova>();
	ArrayList<String> records2 = new ArrayList<String>();
	ArrayList<Casanova> records = new ArrayList<Casanova>();

	// paint the map
	public Main() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map4[i][j] = map[i][j];
				map[i][j] = map4[i][j];

			}
		}
	}

	final int BRICK = 0;
	final int FLOOR = 6;
	final int LADY1 = 7;
	final int LADY2 = 8;
	final int LADY3 = 9;
	final int LADY4 = 10;
	final int LADY5 = 11;
	final int PLAYBOY = 1;
	final int ROSE = 2;
	final int BG = 3;

	int count = 0;

	boolean overlap = false;
	boolean[] ladySpot = { false, false, false, false, false };
	boolean stopped = false;

	Image[] images = { new Image("images\\brick7.png"), new Image("images\\boy.jpg"), new Image("images\\bouquet.jpg"),
			new Image("images\\bg.png"), new Image("images\\brick7.png"), new Image("images\\brick7.png"),
			new Image("images\\floor.png"), new Image("images\\girl1.jpg"), new Image("images\\girl2.jpg"),
			new Image("images\\girl3.jpg"), new Image("images\\girl4.jpg"), new Image("images\\girl5.jpg") };

	int[][] map = {{BRICK, BRICK, BRICK, BRICK, BRICK, BRICK, BRICK, BRICK, BRICK, BG},
		    {BRICK, LADY1, LADY2, LADY3, LADY4, LADY5, FLOOR, FLOOR, BRICK, BG},
		    {BRICK, BRICK, BRICK, FLOOR, ROSE, FLOOR, BRICK, FLOOR, BRICK, BRICK},
		    {BG, BG, BRICK, FLOOR, ROSE, BRICK, BRICK, FLOOR, FLOOR, BRICK},
		    {BG, BG, BRICK, ROSE, FLOOR, ROSE, FLOOR, ROSE, FLOOR, BRICK},
		    {BG, BG, BRICK, FLOOR, FLOOR, FLOOR, BRICK, FLOOR, FLOOR, BRICK},
		    {BG, BG, BRICK, BRICK, FLOOR, FLOOR, BRICK, FLOOR, PLAYBOY, BRICK},
		    {BG, BG, BG, BRICK, BRICK, BRICK, BRICK, BRICK, BRICK, BRICK}};

		    // array for comparison
		    int[][] map2 = {{BRICK, BRICK, BRICK, BRICK, BRICK, BRICK, BRICK, BRICK, BRICK, BG},
		    {BRICK, LADY1, LADY2, LADY3, LADY4, LADY5, FLOOR, FLOOR, BRICK, BG},
		    {BRICK, BRICK, BRICK, FLOOR, ROSE, FLOOR, BRICK, FLOOR, BRICK, BRICK},
		    {BG, BG, BRICK, FLOOR, ROSE, BRICK, BRICK, FLOOR, FLOOR, BRICK},
		    {BG, BG, BRICK, ROSE, FLOOR, ROSE, FLOOR, ROSE, FLOOR, BRICK},
		    {BG, BG, BRICK, FLOOR, FLOOR, FLOOR, BRICK, FLOOR, FLOOR, BRICK},
		    {BG, BG, BRICK, BRICK, FLOOR, FLOOR, BRICK, FLOOR, PLAYBOY, BRICK},
		    {BG, BG, BG, BRICK, BRICK, BRICK, BRICK, BRICK, BRICK, BRICK}};

	int[][] map3 = new int[map.length][map[0].length];

	int[][] map4 = new int[8][10];

	GraphicsContext gc;

	Timer timer1 = new java.util.Timer();
	Timer timer2 = new java.util.Timer();
	LabelChangeTimerTask task = new LabelChangeTimerTask();
	Label timer = new Label();
	Label idRecord = new Label();

	private int time = 0;

	Button undo = new Button("UNDO");
	Button reset = new Button("RESET");
	Button stop = new Button("STOP");
	Button exit = new Button("EXIT");
	Label sokoban = new Label("CASANOVA");
	Label label = new Label("Enter Your ID: ");
	TextField userId = new TextField();
	Button start = new Button();
	Label before = new Label();

	StackPane loginRoot = new StackPane();
	GridPane gp = new GridPane();
	BorderPane gameRoot = new BorderPane();
	BorderPane topBar = new BorderPane();
	VBox other = new VBox();
	StackPane center = new StackPane();
	FlowPane fp = new FlowPane();

	String id = userId.getText();

	// timer for record user's playing time
	class LabelChangeTimerTask extends TimerTask {

		@Override
		public void run() {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					time++;
					int min = time / 60;
					int sec = time % 60;

					timer.setText(String.format("%s : %s", min, sec));
					timer.setStyle("-fx-text-fill: white; -fx-font-size:40pt; -fx-font-weight:bold;");

					Casanova currentPlayer = new Casanova(); // modify

					if (checkup(map) == true) {
						timer1.cancel();
						timer1.purge();
						timer.setText("Success! You finished in " + min + " : " + sec);
						timer.setStyle("-fx-font-size: 18pt;-fx-text-fill: white;");

						undo.setStyle("-fx-background-color: black; -fx-text-fill: black;");
						stop.setStyle("-fx-background-color: black; -fx-text-fill: black;");
						reset.setText("RESTART");
					}
				}
			});
		}
	}

	@Override
	public void start(Stage loginStage) {

		loginRoot.setId("pane");
		Scene loginScene = new Scene(loginRoot, 800, 700);

		loginStage.setScene(loginScene);
		loginStage.setTitle("Login");
		loginRoot.setId("pane");
		loginScene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());

		loginStage.show();

		sokoban.setStyle("-fx-font-size:35pt; -fx-font-weight:bold; -fx-text-fill:white;");

		label.setStyle("-fx-font-size:20pt; -fx-font-weight:bold; -fx-text-fill:white;");

		userId.setPrefWidth(300);
		userId.setMinHeight(45);

		start.setId("start");
		start.setText("START");
		start.setPrefWidth(300);
		start.setMinHeight(45);

		gp.setVgap(15);
		gp.setPadding(new Insets(10, 11, 12, 13));
		gp.setAlignment(Pos.CENTER);
		gp.add(sokoban, 0, 0);
		gp.add(label, 0, 1);
		gp.add(userId, 0, 2);
		gp.add(start, 0, 3);
		loginRoot.getChildren().add(gp);

		Scene gameScene = new Scene(gameRoot, 800, 700);

		// start on the login page which leds to the game page
		start.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Casanova currentPlayer = new Casanova();
				currentPlayer.setId(userId.getText());
				idRecord.setText("USER: " + currentPlayer.getId());
				idRecord.setStyle("-fx-text-fill: yellow; -fx-font-size:20pt; -fx-font-weight:bold;");

				loginStage.setScene(gameScene);
				loginStage.setTitle("Sokoban Game");
				timer1.schedule(task, 0, 1 * 1000);
			}

		});

		// label for the first record
		before.setStyle("-fx-font-size:20pt; -fx-text-fill:yellow;");
		if (Record.readFromFile().toString() == null) {
			before.setText("");
		} else {
			before.setText("RECORD: " + Record.readFromFile().toString());
		}
		other.getChildren().add(before);

		topBar.setLeft(idRecord);
		topBar.setCenter(timer);
		topBar.setRight(other);

		Canvas canvas = new Canvas(600, 500);
		gc = canvas.getGraphicsContext2D();
		center.getChildren().add(canvas);

		fp.setAlignment(Pos.CENTER);
		fp.setHgap(20);
		undo.setPadding(new Insets(10, 10, 10, 10));
		reset.setPadding(new Insets(10, 10, 10, 10));
		stop.setPadding(new Insets(10, 10, 10, 10));
		exit.setPadding(new Insets(10, 10, 10, 10));

		undo.setStyle("-fx-background-color: lightblue; -fx-font-size: 20pt; -fx-font-weight:bold;");
		reset.setStyle("-fx-background-color: lightblue; -fx-font-size: 20pt; -fx-font-weight:bold;");
		stop.setStyle("-fx-background-color: lightblue; -fx-font-size: 20pt; -fx-font-weight:bold;");
		exit.setStyle("-fx-background-color: lightblue; -fx-font-size: 20pt; -fx-font-weight:bold;");

		fp.getChildren().add(undo);
		fp.getChildren().add(reset);
		fp.getChildren().add(stop);
		fp.getChildren().add(exit);

		gameRoot.setTop(topBar);
		gameRoot.setCenter(center);
		gameRoot.setStyle("-fx-border-color:white;");
		gameRoot.setBottom(fp);
		gameRoot.setStyle("-fx-background-color:black;");

		PlayBoy playboy = new PlayBoy();
		playboy.y = 6;
		playboy.x = 8;
		map[playboy.y][playboy.x] = PLAYBOY;

		update();

		// undo action
		undo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (checkup(map) == false) {

					for (int i = 0; i < map.length; i++) {
						for (int j = 0; j < map[i].length; j++) {
							if (count != 0) {

								if (map3[i][j] == PLAYBOY) {
									playboy.y = i;
									playboy.x = j;
								}
								map[i][j] = map3[i][j];
							} else {
								map[i][j] = map[i][j];
							}

						}
					}
				}

				update();
				System.out.println("undo");

			}
		});

		// reset action
		reset.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (checkup(map) == true) {
					timer1 = new Timer();
					task = new LabelChangeTimerTask();
					timer1.schedule(task, time = 0, 1 * 1000);

					undo.setStyle("-fx-background-color: lightblue; -fx-font-size: 20pt; -fx-font-weight:bold;");
					stop.setStyle("-fx-background-color: lightblue; -fx-font-size: 20pt; -fx-font-weight:bold;");

					reset.setText("RESET");
				}
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						map[i][j] = map4[i][j];
						playboy.y = 6;
						playboy.x = 8;

					}
				}

				update();
				System.out.println("reset");

			}
		});

		// stop action
		stop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if (checkup(map) == false) {
					if (stop.getText() == "STOP") {
						timer1.purge();
						timer1.cancel();
						stop.setText("RESTART");
						stop.setPadding(new Insets(10, 5, 10, 5));
						stopped = true;

					} else {
						stop.setText("STOP");
						stopped = false;
						timer1 = new Timer();
						task = new LabelChangeTimerTask();
						timer1.schedule(task, time, 1 * 1000);

					}
				}
			}
		});

		// exit action
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				timer1.cancel();
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Quit Game");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure that you want to quit the game?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					stopped = true;
					String timeT = timer.getText();
					String id = userId.getText();

					System.out.println();
					System.out.println(
							"If the user click 'yes' for terminating the game, the following record is appended. ");

					System.exit(0);

				} else if (result.get() == ButtonType.CANCEL) {

					stop.setText("STOP");
					stopped = false;
					timer1 = new Timer();
					task = new LabelChangeTimerTask();
					timer1.schedule(task, time, 1 * 1000);
					alert.close();
				}
			}

		});

		// keyevent for four arrow buttons
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent event) {

				if (checkup(map) == false) {

					for (int i = 0; i < map3.length; i++) {
						for (int j = 0; j < map3[i].length; j++) {
							map3[i][j] = map[i][j];

						}
					}

					if (event.getCode() == KeyCode.UP) {

						if (map[playboy.y - 1][playboy.x] == FLOOR) {
							if (map2[playboy.y][playboy.x] == ROSE || map2[playboy.y][playboy.x] == PLAYBOY) {
								map[playboy.y][playboy.x] = FLOOR;
								playboy.y--;
								map[playboy.y][playboy.x] = PLAYBOY;
							} else {
								map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
								playboy.y--;
								map[playboy.y][playboy.x] = PLAYBOY;
							}
							boolean ii = true;
							System.out.println("floor and pb" + ii);

						} else if (map[playboy.y - 1][playboy.x] > 5) {
							boolean ik = true;
							System.out.println("lady and pb" + ik);
							if (map2[playboy.y][playboy.x] == ROSE) {
								overlap = true;
								map[playboy.y][playboy.x] = FLOOR;
								playboy.y--;
								map[playboy.y][playboy.x] = PLAYBOY;

							} else {
								overlap = true;
								map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
								playboy.y--;
								map[playboy.y][playboy.x] = PLAYBOY;
							}
						} else if (map[playboy.y - 1][playboy.x] == ROSE) {
							if (map[playboy.y - 2][playboy.x] == ROSE) {
								map[playboy.y - 1][playboy.x] = ROSE;
								map[playboy.y - 2][playboy.x] = ROSE;
							} else if (map2[playboy.y][playboy.x] > 5) {

								if (map[playboy.y - 2][playboy.x] != BRICK) {
									boolean pr = true;
									System.out.println(pr);
									map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
									playboy.y--;
									map[playboy.y][playboy.x] = PLAYBOY;
									map[playboy.y - 1][playboy.x] = ROSE;
								}
							} else if (map[playboy.y - 2][playboy.x] == FLOOR) {
								boolean io = true;
								System.out.println("rose and pb" + io);

								map[playboy.y][playboy.x] = FLOOR;
								playboy.y--;
								map[playboy.y][playboy.x] = PLAYBOY;
								map[playboy.y - 1][playboy.x] = ROSE;
							} else if (map[playboy.y - 2][playboy.x] > 5) {
								boolean id = true;
								System.out.println("rose and lady and pb" + id);
								if (map2[playboy.y][playboy.x] == ROSE) {
									map[playboy.y][playboy.x] = FLOOR;
									playboy.y--;
									map[playboy.y][playboy.x] = PLAYBOY;
									map[playboy.y - 1][playboy.x] = ROSE;
								} else {
									map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
									playboy.y--;
									map[playboy.y][playboy.x] = PLAYBOY;
									map[playboy.y - 1][playboy.x] = ROSE;
								}
							}
						}
					}
					if (event.getCode() == KeyCode.DOWN) {
						if (map[playboy.y + 1][playboy.x] == FLOOR) {
							if (map2[playboy.y][playboy.x] == ROSE || map2[playboy.y][playboy.x] == PLAYBOY) {
								map[playboy.y][playboy.x] = FLOOR;
								playboy.y++;
								map[playboy.y][playboy.x] = PLAYBOY;
							} else {
								map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
								playboy.y++;
								map[playboy.y][playboy.x] = PLAYBOY;
							}
						} else if (map[playboy.y + 1][playboy.x] > 5) {
							if (map2[playboy.y][playboy.x] == ROSE) {
								// overlap = true;
								map[playboy.y][playboy.x] = FLOOR;
								playboy.y++;
								map[playboy.y][playboy.x] = PLAYBOY;
							} else {
								// overlap = true;
								map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
								playboy.y++;
								map[playboy.y][playboy.x] = PLAYBOY;
							}

						} else if (map[playboy.y + 1][playboy.x] == ROSE) {
							if (map[playboy.y + 2][playboy.x] == ROSE) {
								map[playboy.y + 1][playboy.x] = ROSE;
								map[playboy.y + 2][playboy.x] = ROSE;
							} else if (map2[playboy.y][playboy.x] > 5) {
								if (map[playboy.y + 2][playboy.x] != BRICK) {
									map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
									playboy.y++;
									map[playboy.y][playboy.x] = PLAYBOY;
									map[playboy.y + 1][playboy.x] = ROSE;
								}

							} else if (map[playboy.y + 2][playboy.x] == FLOOR) {
								map[playboy.y][playboy.x] = FLOOR;
								playboy.y++;
								map[playboy.y][playboy.x] = PLAYBOY;
								map[playboy.y + 1][playboy.x] = ROSE;
							} else if (map[playboy.y + 2][playboy.x] > 5) {
								if (map2[playboy.y][playboy.x] == ROSE) {
									map[playboy.y][playboy.x] = FLOOR;
									playboy.y++;
									map[playboy.y][playboy.x] = PLAYBOY;
									map[playboy.y + 1][playboy.x] = ROSE;
								} else {
									map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
									playboy.y++;
									map[playboy.y][playboy.x] = PLAYBOY;
									map[playboy.y + 1][playboy.x] = ROSE;
								}
							}
						}
					}

					if (event.getCode() == KeyCode.LEFT) {
						if (map[playboy.y][playboy.x - 1] == FLOOR) {
							if (map2[playboy.y][playboy.x] == ROSE || map2[playboy.y][playboy.x] == PLAYBOY) {
								map[playboy.y][playboy.x] = FLOOR;
								playboy.x--;
								map[playboy.y][playboy.x] = PLAYBOY;
							} else {
								map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
								playboy.x--;
								map[playboy.y][playboy.x] = PLAYBOY;
							}
						} else if (map[playboy.y][playboy.x - 1] > 5) {
							if (map2[playboy.y][playboy.x] == ROSE) {
								overlap = true;
								map[playboy.y][playboy.x] = FLOOR;
								playboy.x--;
								map[playboy.y][playboy.x] = PLAYBOY;
							} else {
								overlap = true;
								map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
								playboy.x--;
								map[playboy.y][playboy.x] = PLAYBOY;
							}

						} else if (map[playboy.y][playboy.x - 1] == ROSE) {
							if (map[playboy.y][playboy.x - 2] == ROSE) {
								map[playboy.y][playboy.x - 1] = ROSE;
								map[playboy.y][playboy.x - 2] = ROSE;
							} else if (map2[playboy.y][playboy.x] > 5) {
								if (map[playboy.y][playboy.x - 2] != BRICK) {
									map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
									playboy.x--;
									map[playboy.y][playboy.x] = PLAYBOY;
									map[playboy.y][playboy.x - 1] = ROSE;
								}

							} else if (map[playboy.y][playboy.x - 2] == FLOOR) {
								map[playboy.y][playboy.x] = FLOOR;
								playboy.x--;
								map[playboy.y][playboy.x] = PLAYBOY;
								map[playboy.y][playboy.x - 1] = ROSE;
							} else if (map[playboy.y][playboy.x - 2] > 5) {
								if (map2[playboy.y][playboy.x] == ROSE) {
									map[playboy.y][playboy.x] = FLOOR; // �눧紐꾩젫...
									playboy.x--;
									map[playboy.y][playboy.x] = PLAYBOY;
									map[playboy.y][playboy.x - 1] = ROSE;
								} else {
									map[playboy.y][playboy.x] = map2[playboy.y][playboy.x]; // �눧紐꾩젫...
									playboy.x--;
									map[playboy.y][playboy.x] = PLAYBOY;
									map[playboy.y][playboy.x - 1] = ROSE;
								}

							}
						}
					}
					if (event.getCode() == KeyCode.RIGHT) {

						if (map[playboy.y][playboy.x + 1] == FLOOR) {

							if (map2[playboy.y][playboy.x] == ROSE || map2[playboy.y][playboy.x] == PLAYBOY) {
								map[playboy.y][playboy.x] = FLOOR;
								playboy.x++;
								map[playboy.y][playboy.x] = PLAYBOY;
							} else {
								map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
								playboy.x++;
								map[playboy.y][playboy.x] = PLAYBOY;
							}

						} else if (map[playboy.y][playboy.x + 1] > 5) {
							if (map2[playboy.y][playboy.x] == ROSE) {
								overlap = true;
								map[playboy.y][playboy.x] = FLOOR;
								playboy.x++;
								map[playboy.y][playboy.x] = PLAYBOY;
							} else {
								overlap = true;
								map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
								playboy.x++;
								map[playboy.y][playboy.x] = PLAYBOY;
							}

						} else if (map[playboy.y][playboy.x + 1] == ROSE) {
							if (map[playboy.y][playboy.x + 2] == ROSE) {
								map[playboy.y][playboy.x + 1] = ROSE;
								map[playboy.y][playboy.x + 2] = ROSE;
							} else if (map2[playboy.y][playboy.x] > 5) {
								if (map[playboy.y][playboy.x + 2] != BRICK) {
									map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
									playboy.x++;
									map[playboy.y][playboy.x] = PLAYBOY;
									map[playboy.y][playboy.x + 1] = ROSE;
								}

							} else if (map[playboy.y][playboy.x + 2] == FLOOR) {
								map[playboy.y][playboy.x] = FLOOR;
								playboy.x++;
								map[playboy.y][playboy.x] = PLAYBOY;
								map[playboy.y][playboy.x + 1] = ROSE;
							} else if (map[playboy.y][playboy.x + 2] > 5) {
								if (map2[playboy.y][playboy.x] == ROSE) {
									map[playboy.y][playboy.x] = FLOOR;
									playboy.x++;
									map[playboy.y][playboy.x] = PLAYBOY;
									map[playboy.y][playboy.x + 1] = ROSE;
								} else {
									map[playboy.y][playboy.x] = map2[playboy.y][playboy.x];
									playboy.x++;
									map[playboy.y][playboy.x] = PLAYBOY;
									map[playboy.y][playboy.x + 1] = ROSE;
								}

							}

						}

					}

					checkup(map);
					count++;
					update();

				}

			}
		});
	}

	// paint and repaint
	public void update() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				gc.drawImage(images[map[i][j]], j * 60, i * 60, 60.0, 60.0);
			}
		}
	}

	// check to see if the game finishes
	public boolean checkup(int[][] map) {

		boolean rose = false;

		for (int i = 1; i < 6; i++) {

			if (map[1][i] == ROSE) {
				ladySpot[i - 1] = true;

			} else if (map[1][i] != ROSE) {
				ladySpot[i - 1] = false;

			}

		}

		for (int i = 0; i < 5; i++) {
			System.out.println("ladySpot " + ladySpot[i]);
		}

		int count = 0;
		for (int i = 0; i < ladySpot.length; i++) {

			if (ladySpot[i] == false) {
				rose = false;
			} else {
				count++;
				if (count == 5) {
					rose = true;
					stopped = true;

					String id = userId.getText();
					Casanova player = new Casanova(id, time);
					System.out.println("00001 : " + player.toString());
					records.add(player);
					Record.writeToFile(player);
					System.out.println(Record.readFromFile());
				}
			}
		}

		return rose;

	}

	public static void main(String[] args) {
		launch(args);
	}
}

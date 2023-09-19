package game;

import java.io.PrintWriter;

import environment.Direction;
import gui.GameGuiMain;

public class SendDirectionThread extends Thread{

	private GameGuiMain gameGui;
	private PrintWriter output;

	public SendDirectionThread(GameGuiMain gameGui, PrintWriter output) {
		this.gameGui = gameGui;
		this.output = output;

	}

	@Override
	public void run() {
		Direction d = null;
		while(!this.isInterrupted()) {
			d = gameGui.getBoardGui().getLastPressedDirection();

			if(d != null) {
				output.println(d.name());
				System.out.println(d);
				d = null;
				gameGui.getBoardGui().clearLastPressedDirection();

			}

		}

	}

}

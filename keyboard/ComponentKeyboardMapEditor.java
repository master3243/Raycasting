/*
 * Abdulrahman Alabdulkareem 782435
 * May 17, 2016
 */
package raycasting.keyboard;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import raycasting.Direction;
import raycasting.entities.Player;;

public class ComponentKeyboardMapEditor {

	private JComponent comp;

	public ComponentKeyboardMapEditor(JComponent comp) {
		this.comp = comp;
	}

	private int counter = 1;

	public void addComand(KeyStroke kePressed, Action actPressed, Action actReleased) {
		comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(kePressed, counter);
		comp.getActionMap().put(counter, actPressed);
		counter++;
		comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(kePressed.getKeyCode(), 0, true),
				counter);
		comp.getActionMap().put(counter, actReleased);
		counter++;
	}

	public void addComand(KeyStroke kePressed, Action actPressed) {
		comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(kePressed, counter);
		comp.getActionMap().put(counter, actPressed);
		counter++;
	}

	public void bindToMoveInDirection(Player player, KeyStroke kePressed, Direction direction) {
		Action holdAction = null;
		Action releaseAction = null;

		if (direction.getDirectionNumber() % 90 != 0)
			throw new IllegalArgumentException("Direction has to be a multiple of 90");

		switch ((int) direction.getDirectionNumber()) {
		case 0:
			holdAction = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.startMovingForward();
				}
			};
			releaseAction = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.stopMovingForward();
				}
			};
			break;
		case 90:
			holdAction = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.startMovingLeft();
				}
			};
			releaseAction = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.stopMovingLeft();
				}
			};
			break;
		case 180:
			holdAction = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.startMovingBackward();
				}
			};
			releaseAction = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.stopMovingBackward();
				}
			};
			break;
		case 270:
			holdAction = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.startMovingRight();
				}
			};
			releaseAction = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.stopMovingRight();
				}
			};
			break;
		}
		addComand(kePressed, holdAction, releaseAction);
	}

	public void bindToRotateInDirection(Player player, KeyStroke kePressed, boolean counterClockwise) {
		Action holdAction = null;
		Action releaseAction = null;

		if (counterClockwise) {
			holdAction = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.startRotatingLeft();
				}
			};
			releaseAction = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.stopRotatinggLeft();
				}
			};
		} else {
			holdAction = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.startRotatingRight();
				}
			};
			releaseAction = new AbstractAction() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					player.stopRotatingRight();
				}
			};
		}
		
		addComand(kePressed, holdAction, releaseAction);
	}
}

package gu.dtalk;

public class RootMenu extends MenuItem {

	public RootMenu() {
		setName("root");
		addChilds(Items.makeQuit());
	}

}

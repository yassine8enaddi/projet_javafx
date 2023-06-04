/*module miniProjet {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
}*/
module miniProjet {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
	requires javafx.base;

    opens application to javafx.graphics, javafx.fxml, javafx.base;
}

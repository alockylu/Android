<?php

class Model {
	public static function getDepartmentsList() {
		return (new DB())->getArrFromQuery(
			"SELECT *
			FROM `departments` ");
	}

	public static function getGroup($id) {
		return (new DB())->getArrFromQuery("SELECT * FROM `departments` where id=$id");
	}
}
?>
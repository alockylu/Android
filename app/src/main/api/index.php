<?php
require 'config.php';
require 'db.php';
require 'model.php';

header('Content-type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-methods: GET, PUT, POST, DELETE, OPTIONS');
header('Access-Control-Allow-Headers: Origin, Content-Type, X-Auth-Token, Authorization');
$action = $_REQUEST['action'];
if ($action == 'get_departments_list') {
	$data = Model::getDepartmentsList();
}  elseif($action == 'get_department' && isset($_REQUEST['dep_id'])) {
	$group = Model::getGroup($_REQUEST['dep_id']);

	if (count($group) > 0) {
		$data = $group[0];
	} else {
		$data = ['err' => 'no department found'];
	}
} else {
	$data = ['err' => 'no action was sent'];
}
echo json_encode($data);
?>
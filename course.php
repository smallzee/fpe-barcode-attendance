<?php
/**
 * Created by PhpStorm.
 * User: Tech4all
 * Date: 2021-08-12
 * Time: 15:15
 */

$page_title = "All Course";
require_once 'config/core.php';
if (!is_login()){
    redirect(base_url('index.php'));
    return;
}

if (isset($_POST['add'])){
    $title = strtolower($_POST['title']);
    $code = $_POST['code'];
    $dept = $_POST['dept'];
    $level = $_POST['level'];

    $sql = $db->query("SELECT * FROM course WHERE title='$title' and code='$code' and department='$dept' and level='$level' ");

    if ($sql->rowCount() >= 1){
        $error[] = "Course has already exist";
    }

    $error_count = count($error);
    if ($error_count ==  0){

        $in = $db->query("INSERT INTO course (title,code,department,level)VALUES ('$title','$code','$dept','$level')");

        set_flash("Course has been added successfully","info");

    }else{
        $msg = ($error_count == 1) ? 'An error occurred' : 'Some error(s) occcurred';
        foreach ($error as $value){
            $msg.='<p>'.$value.'</p>';
        }
        set_flash($msg,'danger');
    }
}

require_once 'libs/head.php';
?>

<div id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" data-dismiss="modal" aria-hidden="true" class="close">×</button>
                <h4 id="myModalLabel" class="modal-title">Add Course</h4>
            </div>
            <div class="modal-body">
                <form action="" method="post">

                    <div class="form-group">
                        <label for="">Course Title</label>
                        <input type="text" class="form-control" name="title" required placeholder="Course Title" id="">
                    </div>

                    <div class="form-group">
                        <label for="">Course Code</label>
                        <input type="text" name="code" placeholder="Course Code" class="form-control" required id="">
                    </div>

                    <div class="form-group">
                        <label for="">Department</label>
                        <select name="dept" class="form-control" required id="">
                            <option value="" selected disabled>Select</option>
                            <?php
                            $sql = $db->query("SELECT * FROM departments ORDER BY name");
                            while ($rs = $sql->fetch(PDO::FETCH_ASSOC)){
                                ?>
                                <option value="<?= $rs['id'] ?>"><?= ucwords($rs['name']) ?></option>
                                <?php
                            }
                            ?>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="">Level</label>
                        <select name="level" class="form-control" required id="">
                            <option value="" selected>Select</option>
                            <?php
                            foreach (array('nd 1 ft','nd 2 ft','nd 1 dpt','nd 2 dpt','nd rpt yr1','nd rpt yr2','nd rpt yr3','hnd 1 ft','hnd 2 ft','hnd 1 dpt','hnd 2 dpt') as $value){
                                ?>
                                <option value="<?= $value ?>"><?= strtoupper($value) ?></option>
                                <?php
                            }
                            ?>
                        </select>
                    </div>

                    <div class="form-group">
                        <input type="submit" name="add" class="btn btn-primary" value="Submit" id="">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="col-md-12">

    <!-- Default box -->
    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title"><?= $page_title ?></h3>
            <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip"
                        title="Collapse">
                    <i class="fa fa-minus"></i>
                </button>
            </div>
        </div>
        <div class="box-body">

            <button data-toggle="modal" style="margin-bottom: 20px;" data-target="#myModal" class="btn btn-primary ">Add Course</button>

            <?php flash() ?>

            <div class="table-responsive">
                <table class="table table-bordered" id="example1">
                    <thead>
                    <tr>
                        <th>SN</th>
                        <th>Course Code</th>
                        <th>Course Title</th>
                        <th>Department</th>
                        <th>Level</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th>SN</th>
                        <th>Course Code</th>
                        <th>Course Title</th>
                        <th>Department</th>
                        <th>Level</th>
                    </tr>
                    </tfoot>
                    <tbody>
                        <?php
                            $sql = $db->query("SELECT c.*, d.name FROM course c INNER JOIN departments d ON c.department = d.id ORDER BY c.id DESC");
                            while ($rs = $sql->fetch(PDO::FETCH_ASSOC)){
                                ?>
                                <tr>
                                    <td><?= $sn++ ?></td>
                                    <td><?= ucwords($rs['title']) ?></td>
                                    <td><?= strtoupper($rs['code']) ?></td>
                                    <td><?= ucwords($rs['name']) ?></td>
                                    <td><?= strtoupper($rs['level']) ?></td>
                                </tr>
                                <?php
                            }
                        ?>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>



<?php require_once 'libs/foot.php';?>

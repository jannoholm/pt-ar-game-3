Result: <?php

if ($_POST['data'])
{
  $file_handle = fopen('leaderboard_1', 'w');
  fwrite($file_handle, $_POST['data']);
  fclose($file_handle);
  echo 'OK';
} else {
  echo 'No data';    
}
?>
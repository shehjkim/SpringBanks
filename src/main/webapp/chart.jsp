<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <!--Load the AJAX API-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', '부서');
        data.addColumn('number', '인원');		//컬럼별 속성정의
        
        var arr = [];
        //ajax
        $.ajax({
        	url : 'getDay' ,
        	async: false,		//동기식
        	dataType: 'json',
        	success: function(result){ //@ResponseBody에 있는 값을 result에 담음
        		console.log(result)
        		//서버에ㅓㅅ 받아온 데이터 [{},{}] -> [[],[]]
        		 for(lists of result ) { //result를 lists라는 변수에 담아줌 		//배열형태로 
 	       			//arr.push(   [lists.name, parseInt(lists.cnt) ]    );
        			 arr.push(   [lists.sale_date, parseInt(lists.sum_price) ]    );
        		} 
        		console.log(arr);
        	}
        	
        	
        });
        data.addRows(arr);
        
/*         data.addRows([
          ['개발', 3],
          ['인사', 1],
          ['기획', 1],

        ]); */

        // Set chart options
        var options = {'title':'부서별 인원 수',
                       'width':400,
                       'height':300};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
  </head>

  <body>
    <!--Div that will hold the pie chart-->
    <div id="chart_div"></div>
  </body>
</html>
$(function() {

  var type_sys = "Etl-System";
  var type_job = "Etl-Job";
  var job_templ = "<tr><td>Description</td><td colspan='3'>${description}</td></tr>"
        +"<tr><td>ETL System</td><td>${etl_system}</td><td>etl_job</td><td>${etl_job}</td>"
        +"</tr>"
        +"<tr><td>ETL Server</td><td>${etl_server }</td><td>Job Type</td><td>${jobtype}</td>"
        +"</tr>"
        +"<tr><td>Last Start Time</td><td>${last_starttime }</td><td>Last End Time</td><td>${last_endtime }</td>"
        +"</tr>"
        +"<tr class='{@if last_jobstatus=='Done'}success{@else}warning{@/if}'><td>Last Job Status</td><td>${last_jobstatus }</td><td>Last TxDate</td><td>${last_txdate}</td>"
        +"</tr>"
        +"<tr><td>Enable</td><td>${enable }</td><td>Frequency</td><td>${frequency}</td>"
        +"</tr>"
        +"<tr><td>Last File Count</td><td>${last_filecnt }</td><td>Time Trigger</td><td>${timetrigger}</td><td></td>"
        +"</tr>"
        +"<tr><td>Last Cube Status</td><td>${last_cubestatus}</td><td>Cube Flag </td><td>${cubeflag }</td>"
        +"</tr>"
        +"<tr><td>checkflag</td><td>${checkflag}</td><td>Auto Off</td><td>${autooff}</td>"
        +"</tr>"
        +"<tr><td>Check Calendar</td><td>${checkcalendar}</td><td>Calendarbu</td><td>${calendarbu }</td>"
        +"</tr>"
        +"<tr><td>Running Script</td><td>${runningscript}</td><td>Job Session ID</td><td>${jobsessionid }</td>"
        +"</tr>"
        +"<tr><td>Expected Record </td><td>${expectedrecord }</td><td>Check Last Status</td><td>${checklaststatus}</td>"
        +"</tr>";
  var sys_templ = "<tr>"
        +"<td width='200'>ETL System</td><td>${etl_system}</td>"
        +"</tr><tr>"
        +"<td>Description</td><td>${description}</td>"
        +"</tr><tr>"
        +"<td>Data Keeppe Riod</td><td>${datakeepperiod}</td>"
        +"</tr><tr>"
        +"<td>Log Keeppe Riod</td><td>${logkeepperiod}</td>"
        +"</tr><tr>"
        +"<td>Record Keeppe Riod</td><td>${recordkeepperiod}</td>"
        +"</tr>";
  var job_log_templ = "<tr><td>SESSIONID</td><td>SCRIPTFILE</td><td>TXDATE</td><td>STARTTIME</td><td>ENDTIME</td><td>RTNCODE</td><td>SECONDS</td></tr>"
  +"{@each logs as it}"
  +"<tr><td>${it.jobsessionid}</td><td><a href='/showlog/${it.etl_system} ${it.txdate} ${it.scriptfile}.${it.jobsessionid}.log' target='_blank'>${it.scriptfile}</a></td><td>${it.txdate}</td><td>${it.starttime}</td><td>${it.endtime}</td><td>${it.returncode}</td><td>${it.seconds}</td>"
  +"</tr>{@/each}";

// 793290 song tie zheng

  $("#btn_job_logs").click(function(){
    var jobid = $("#job_sys_id").val();
    $.get("/showloglist/"+jobid).done(function(val){
      var joblogs = eval('(' + val + ')');
      var s = juicer(job_log_templ, joblogs);
      console.log(s);
      $("#tab_job_log").html(s);
      $("#myModal").modal({keyboard:true,backdrop: false});
    });
  });

  $("#btn_show_deps").click(function(){
    var jobid = $("#job_sys_id").val();
    window.open("/showdep/"+jobid);
  });

  $.get("/refdata/all/all").done(function(val){
      var json = eval('(' + val + ')');
      var syss = json.systems;
      var defaultData = [{
        text: 'CPAML',
        nodes: []
      }];
      for (var i =0;i<syss.length;i++){
        // console.log(syss[i])
        var jobs = json.jobs[syss[i]];
        d = {
          text: syss[i],
          type: type_sys,
          nodes: []
        }
        for (var j=0;j<jobs.length;j++){
          // console.log(syss[i]+'->'+jobs[j].etl_job)
          d.nodes.push({
            text: jobs[j].etl_job,
            type: type_job
          });
        }
        defaultData[0].nodes.push(d);
      }
      $('#treeview1').treeview({
          color: "#428bca",
          data: defaultData,
          showTags: true,
          onNodeSelected: function(e,node){
            jobId = node.text;
            type = node.type;
            $("#job_sys_id").val(jobId);
            desc = '';
            // get job info
            if (type == type_job){
              $.get("/refdata/job/"+jobId).done(function(val){
                var jobinfo = eval('(' + val + ')');
                var s = juicer(job_templ, jobinfo);
                desc = jobinfo.description;
                $("#tab_job_info").html(s);
                $.each($("span[param='job_info_title']"), function(idx, obj){
                    $(obj).html(jobId + " - " + desc);
                });
                $("#sys_tools").hide()
                $("#job_tools").show();
              });
            }else{
              $.get("/refdata/sys/"+jobId).done(function(val){
                var jobinfo = eval('(' + val + ')');
                s = juicer(sys_templ, jobinfo);
                desc = jobinfo.description;
                $("#tab_job_info").html(s);
                $.each($("span[param='job_info_title']"), function(idx, obj){
                    $(obj).html(jobId + " - " + desc);
                });
                $("#job_tools").hide();
                $("#sys_tools").show();
              });
            }
            $("#div_sys_job_info").show();
          }
      });
    });
});
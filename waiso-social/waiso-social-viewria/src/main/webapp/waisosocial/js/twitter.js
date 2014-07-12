/**
 * Todos os direitos reservados a Waiso
 * 
 * Copyright - Waiso 
 * 
 * @author Equipe Waiso-Developer - Waiso
 * 
 */
jQuery.twitter = function(){
	var twitterClass = function(){
		this.init = function(){
			$("#chkRetweetUsers").change(function(){
				if(this.checked){
					//_self.executionThread("retweets", "on");
				}else{
					//_self.executionThread("retweets", "off");
				}
			});

			$("#chkTweets").change(function(){
				if(this.checked){
					//_self.executionThread("tweets", "on");
				}else{
					//_self.executionThread("tweets", "off");
				}
			});
			
			$("#chkUsers").change(function(){
				if(this.checked){
					//_self.executionThread("users", "on");
				}else{
					//_self.executionThread("users", "off");
				}
			});
			
			_self.loadingInitialData();
		};
		
		this.loadingInitialData = (function(){
			$.ajax({
				type:"POST",
				url:url,
				async:false,
				data: ({webClassId : "ping", invoke:"ping"}),
				dataType:"json",
				success: function(jsonReturn){
					var consequence = jsonReturn.consequence;
					if(consequence == "NO_ACCESS"){
						document.location.href = jsonReturn.page;
					}else if(consequence == "ERROR"){
						redirectInternalServerError();
					}else if (consequence == "SUCCESS"){
						_self.finExecutionThread();
						_self.findUsers();
						_self.findTweets();
					}
				}
			});
		});
		
		this.finExecutionThread = (function(){
			$.ajax({
				type:"POST",
				url:url,
				async:false,
				data: ({webClassId : "threads", invoke:"executions"}),
				dataType:"json",
				success: function(jsonReturn){
					var consequence = jsonReturn.consequence;
					if(consequence == "NO_ACCESS"){
						document.location.href = jsonReturn.page;
					}else if(consequence == "ERROR"){
						redirectInternalServerError();
					}else if (consequence == "SUCCESS"){
						var threads = jsonReturn.dado;
						$('#chkRetweetUsers').prop('checked', threads["retweet"]);
						$('#chkTweets').prop('checked', threads["tweet"]);
						$('#chkUsers').prop('checked', threads["user"]);
						
						$(".iButton-icons").iButton({
							labelOn: "<i class='icon-ok'></i>",
						    labelOff: "<i class='icon-remove'></i>",
						    handleWidth: 30
						});
					}
				}
			});
		});
		
		this.executionThread = (function(thread, execution){
			$.ajax({
				type:"POST",
				url:url,
				async:false,
				data: ({webClassId : "threads", invoke:"execution", thread:thread, execution:execution}),
				dataType:"json",
				success: function(jsonReturn){
					var consequence = jsonReturn.consequence;
					if(consequence == "NO_ACCESS"){
						document.location.href = jsonReturn.page;
					}else if(consequence == "ERROR"){
						redirectInternalServerError();
					}else if (consequence == "SUCCESS"){
						//
					}
				}
			});
		});

		this.findUsers = function(){
			$.ajax({
				type:"POST",
				url:url,
				async:false,
				data: ({webClassId : "users", invoke:"getUsers"}),
				dataType:"json",
				success: function(jsonReturn){
					var consequence = jsonReturn.consequence;
					if(consequence == "NO_ACCESS"){
						document.location.href = jsonReturn.page;
					}else if(consequence == "ERROR"){
						redirectInternalServerError();
					}else if (consequence == "SUCCESS"){
						var usersRetweets = jsonReturn.dado["users_retweets"];
						jQuery.each(usersRetweets, function(i, userRetweets){
							_self.insertLineTable("tableUsersRetweets", userRetweets);
		                });
						
						var usersMain = jsonReturn.dado["users_main"];
						jQuery.each(usersMain, function(i, userMain){
							_self.insertLineTable("tableUsersMain", userMain);
		                });
						
						$(".dTable").dataTable({
							bJQueryUI: false,
						    bAutoWidth: false,
						    sPaginationType: "full_numbers",
						    sDom: "<\"table-header\"fl>t<\"table-footer\"ip>"
						});
						
						var usersFollow = jsonReturn.dado["users_follow"];
						jQuery.each(usersFollow, function(i, userFollow){
							_self.insertLineUsers("follow", userFollow);
		                });
						
						var usersUnfollow = jsonReturn.dado["users_unfollow"];
						jQuery.each(usersUnfollow, function(i, userUnfollow){
							_self.insertLineUsers("unfollow", userUnfollow);
		                });
					}
				}
			});
		};
		
		this.insertLineTable = (function(table, user){
			var line = [""];
			line.push("<tr><td><a href='https://www.twitter.com/" + user + "' target='_blank'>@" + user + "</a></td></tr>");
			$("#" + table).append(line.join(""));
		});
		
		this.findTweets = (function(){
			$.ajax({
				type:"POST",
				url:url,
				async:false,
				data: ({webClassId : "tweets", invoke:"getTweets"}),
				dataType:"json",
				success: function(jsonReturn){
					var consequence = jsonReturn.consequence;
					if(consequence == "NO_ACCESS"){
						document.location.href = jsonReturn.page;
					}else if(consequence == "ERROR"){
						redirectInternalServerError();
					}else if (consequence == "SUCCESS"){
						var tweetsSent = jsonReturn.dado["tweets_sent"];
						jQuery.each(tweetsSent, function(i, tweetSent){
							_self.insertLineScrollableTweets(tweetSent);
		                });
						$("#countTweets").empty().append(tweetsSent.length);
					}
				}
			});
		});
		
		this.insertLineScrollableTweets = (function(tweet){
			var line = [""];
			line.push('<div class="box-section news with-icons">');
			line.push('<div class="avatar blue"><i class="icon-twitter icon-2x"></i></div>');
			//line.push('<div class="news-time"><span>06</span> jan </div>');
			line.push('<div class="news-content">');
			//line.push('<div class="news-title"><a href="#">Twitter Bootstrap v3.0 is coming!</a></div>');
			line.push('<div class="news-text">');
			line.push(tweet);
			line.push('</div>');
			line.push('</div>');
			line.push('</div>');
			$("#scrollableTweets").append(line.join(""));
		});
		
		this.insertLineUsers = (function(action, user){
			var line = [""];
			line.push('<div class="box-section news with-icons">');
			line.push('<div class="avatar blue"><i class="icon-twitter icon-2x"></i></div>');
			//line.push('<div class="news-time"><span>06</span> jan </div>');
			line.push('<div class="news-content">');
			line.push('<div class="news-title"><a href="https://twitter.com/' + user + '">@' + user + '</a></div>');
			line.push('<div class="news-text">');
			if(action == "follow"){
				line.push("Amizade Construida!");
			}else{
				line.push("Amizade Desfeita!");
			}
			line.push('</div>');
			line.push('</div>');
			line.push('</div>');
			$("#scrollableUsers").append(line.join(""));
		});
		
		var _self = this;
	};
	return new twitterClass();
};
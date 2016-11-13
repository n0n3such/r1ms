
/* scripts for the r1 project */


$("#msg-submit").on('click', function(e) {
	var origin = document.location.origin;
	var url = origin + "/msg";
	var msg = $("#msg-textarea").val();
	var jqxhr = $.ajax({
		url: url,
		type: "POST",
		data: msg,
		cache: false,
		headers: { "Pragma": "no-cache", "Cache-Control": "no-cache" },
		contentType: "text/plain; charset=utf-8"
	});
	jqxhr.done( function(data) {
		var status = data.status;
		if (status == "ok") {
			console.log("status ok " + data.key);
			var key		= data.key;
			var image	= data.image;
			$("#msg-textarea").val("");
			$("#msg-key-text").html(key);
			var html = '<img class="qrcode" src="data:image/png;base64,' + image + '">';
			$("#msg-key-image").html(html);
			$("#msg-msg-image").html("");
		}
		else {
			console.log("status not ok " + data.reason);
			var reason = data.reason;
			$("#msg-key-image").html("");
			$("#msg-key-text").html(reason);
		}
	})
	jqxhr.fail( function() {
		$("#msg-key-text").val("an error occurred, please try again later");
	})
})

$("#msg-textarea").on('click', function(e) {
	$("#msg-key-image").html("");
	$("#msg-key-text").html("");
	$("#msg-msg-image").html("");
	$("#msg-msg-text").html("");
})


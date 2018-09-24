/**
 * Created by remi on 17/01/15.
 */
(function () {

    var uploadfiles = document.querySelector('#uploadfiles');
    uploadfiles.addEventListener('change', function () {
        var files = this.files;
        for(var i=0; i<files.length; i++){
            uploadFile(this.files[i]);
        }

    }, false);

    

    /*document.getElementById("filepicker").addEventListener("change", function(event) {
    	  let output = document.getElementById("listing");
    	  let files = event.target.files;

    	  for (let i=0; i<files.length; i++) {
    	    let item = document.createElement("li");
    	    item.innerHTML = files[i].webkitRelativePath;
    	    output.appendChild(item);
    	  };
    	}, false);    */
    
    /**
     * Upload a file
     * @param file
     */
    function uploadFile(file){
    	console.log(file);
    	
    	$.ajax({
    	    url: "../Upload",
    	    type: "POST",
    	    beforeSend: function(request) {
    	        request.setRequestHeader("Filename", file.name+","+file.type+","+file.size);
    	      },
    	    data: file,
    	    processData: false,
    	    contentType: false,
    	    success: function (res){
    	      document.getElementById("response").innerHTML = res; 

    	    }
    	  });
        /*var url = "../Upload";
        var xhr = new XMLHttpRequest();
        var fd = new FormData();
        xhr.open("POST", url);
        xhr.responseType = 'arraybuffer';
        //xhr.setRequestHeader('Content-Type', 'application/x-compressed-tar');
        xhr.setRequestHeader("filename",file.name)
        xhr.setRequestHeader("filename",file.type)
        xhr.setRequestHeader("filename",file.size)
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                // Every thing ok, file uploaded
//                console.log(xhr.responseText); // handle response.
            }
        };
        fd.append('uploaded_file', file);
        xhr.send(fd);*/
    }
}());
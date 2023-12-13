<!DOCTYPE html>
<html>
<head>
    <title>Room View</title>
</head>
<body>
<h1>Welcome to the Room View!</h1>

<%-- Display the room details --%>
<p>Room ID: <%= request.getAttribute("roomId") %></p>
<p id="allusername"> </p>

<div id="content">
    <div id="category_chooser">
        <p>The loai nhac</p>
        <button id="Vpop">VPop</button>
        <button id="Kpop">KPop</button>
    </div>

    <audio id="audio" controls="controls">
        <source id="srcAudio" src="/music/sontungMTP/Chung%20Ta%20Cua%20Hien%20Tai%20-%20Son%20Tung%20M-TP.mp3"/>
    </audio>

    <div id="question"></div>
    <div id="options"></div>
    <div id="result"></div>
</div>


<div id="countdown"></div>
<!-- Add your HTML and JavaScript code to create the desired view -->
<!-- ... -->

<script>
    // Establish WebSocket connection
    var roomId = '<%= request.getAttribute("roomId") %>';
    var username = '<%= request.getAttribute("username")%>';
    var webSocket = new WebSocket("ws://" + window.location.host  + "/room/" + roomId + "?" + "username=" + username);
    console.log(webSocket);
    var allusername = document.getElementById('allusername');
    var content = document.getElementById('content');


    // Handle WebSocket events
    webSocket.onopen = function(event) {
        console.log("WebSocket connection opened");
        sendMessage(username);
    };

    webSocket.onmessage = function(event) {
        var message = event.data;
        console.log("WebSocket message received: " + message);
        if(message.startsWith("Question")){
            categoryChooser.style.display = 'none';
            displayQuestion();
        } else {
            allusername.textContent = message;
        }
    };

   /* webSocket.onclose = function(event) {
        console.log("WebSocket connection closed");
    };*/

    var vPopBtn = document.getElementById('Vpop');
    var kPopBtn = document.getElementById('Kpop');
    var content = document.getElementById('content');
    var categoryChooser = document.getElementById("category_chooser");

   vPopBtn.onclick = function () {
      sendMessage("Question")
   };

    kPopBtn.onclick = function () {
        sendMessage("Question")
    }
    // Send WebSocket messages
    function sendMessage(message) {
        webSocket.send(message);
    }

    var questions = [
        {
            audio:"/music/sontungMTP/Chung%20Ta%20Cua%20Hien%20Tai%20-%20Son%20Tung%20M-TP.mp3",
            question: "Son Tung MTP là ai?",
            options: ["A. London", "B. Paris", "C. Việt Nam", "D. Sơn Tùng"],
            answer: "D. Sơn Tùng"
        },
        {
            audio:"/music/sontungMTP/MuonRoiMaSaoCon.mp3",
            question: "Doan nhac nay la gi?",
            options: ["A. London", "B. Paris", "C. Việt Nam", "D. Sơn Tùng"],
            answer: "D. Sơn Tùng"
        },
        {
            audio:"/music/emcuanagayhomequa",
            question: "Son Tung MTP là ai?",
            options: ["A. London", "B. Paris", "C. Việt Nam", "D. Sơn Tùng"],
            answer: "D. Sơn Tùng"
        },
        {
            audio:"src/main/webapp/music/sontungMTP/Chung Ta Cua Hien Tai - Son Tung M-TP.mp3",
            question: "Son Tung MTP là ai?",
            options: ["A. London", "B. Paris", "C. Việt Nam", "D. Sơn Tùng"],
            answer: "D. Sơn Tùng"
        },
        // Add more questions as needed
    ];

    var currentQuestionIndex = 0;
    var countdownElement = document.getElementById("question");
    var optionsElement = document.getElementById("options");
    var resultElement = document.getElementById("result");
    var srcAudio = document.getElementById("srcAudio");
    var audioPlayer = document.getElementById("audio");

    function displayQuestion() {
        var currentQuestion = questions[currentQuestionIndex];
        countdownElement.innerHTML = "Question: " + currentQuestion.question;
        srcAudio.setAttribute("src", currentQuestion.audio);

        optionsElement.innerHTML = "";
        for (var i = 0; i < currentQuestion.options.length; i++) {
            var option = currentQuestion.options[i];
            var label = document.createElement("label");
            var input = document.createElement("input");
            input.type = "radio";
            input.name = "answer";
            input.value = option;
            label.appendChild(input);
            label.appendChild(document.createTextNode(option));
            optionsElement.appendChild(label);
            optionsElement.appendChild(document.createElement("br"));
        }
        audioPlayer.load();
        audioPlayer.play();

        startCountdown();
        setTimeout(showAnswer, 10000); // 10 seconds
    }

    function showAnswer() {
        var currentQuestion = questions[currentQuestionIndex];
        var selectedOption = document.querySelector('input[name="answer"]:checked');

        if (selectedOption && selectedOption.value === currentQuestion.answer) {
            resultElement.innerHTML = "Correct answer!";
            resultElement.style.color = "green";
        } else {
            resultElement.innerHTML = "Wrong answer!";
            resultElement.style.color = "red";
        }

        setTimeout(nextQuestion, 2000); // 2 seconds
    }

    function nextQuestion() {
        resultElement.innerHTML = "";
        currentQuestionIndex++;

        if (currentQuestionIndex < questions.length) {
            displayQuestion();
        } else {
            countdownElement.innerHTML = "Quiz over!";
            optionsElement.innerHTML = "";
        }
    }

    function startCountdown() {
        var seconds = 10;

        function updateCountdown() {
            countdownElement.textContent = seconds;
            seconds--;

            if (seconds < 0) {
                countdownElement.textContent = "Countdown is over!";
            } else {
                setTimeout(updateCountdown, 1000);
            }
        }

        updateCountdown();
    }
</script>
</body>
</html>
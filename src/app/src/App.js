import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

var defaultCardPack = [
	"Flying sex snakes.",
	"Michelle Obama`s arms.",
	"German dungeon porn.",
	"White people.",
	"Getting so angry that you pop a boner.",
	"Tasteful sideboob.",
	"Praying the gay away.",
	"Two midgets shitting into a bucket.",
	"MechaHitler.",
	"Being a motherfucking sorcerer.",
	"A disappointing birthday party.",
	"Puppies!",
	"A windmill full of corpses.",
	"Guys who don`t call.",
	"Racially-biased SAT questions.",
	"Dying.",
	"Steven Hawking talking dirty.",
	"Being on fire.",
	"A lifetime of sadness.",
	"An erection that lasts longer than four hours.",
	"AIDS.",
	"Same-sex ice dancing.",
	"Glenn Beck catching his scrotum on a curtain hook.",
	"The Rapture.",
	"Pterodactyl eggs.",
	"Crippling debt.",
	"Eugenics.",
	"Exchanging pleasentries.",
	"Dying of dysentry.",
	"Roofies.",
	"Getting naked and watching Nickelodeon.",
	"The forbidden fruit.",
	"Republicans.",
	"The Big Bang.",
	"Anal beads.",
	"Amputees.",
	"Men.",
	"Surprise sex!",
	"Kim Jong-il.",
	"Concealing a boner.",
	"Agriculture.",
	"Glenn Beck being harried by a swarm of buzzards.",
	"Making a pouty face.",
	"A salty surprise.",
	"The Jews.",
	"Charisma.",
	"YOU MUST CONDUCT ADDITIONAL PYLONS.",
	"Panda sex.",
	"Taking off your shirt.",
	"A drive-by shooting.",
	"Ronald Reagan.",
	"Morgan Freeman`s voice.",
	"Breaking out into song and dance.",
	"Jewish fraternities.",
	"Dead babies.",
	"Masturbation.",
	"Hormone injections.",
	"All-you-can-eat shrimp for $4.99.",
	"Incest.",
	"Scalping."
];
var defaultQuestionsPack = [
	"__________? There`s an app for that.",
	"Why can`t I sleep at night?",
	"What`s that smell?",
	"I got 99 problems but __________ aint one.",
	"Maybe she`s born with it. Maybe it`s __________.",
	"What`s the next Happy Meal toy?",
	"It`s a pity that kids these days are all getting involved with __________.",
	"What`s that sound?",
	"What ended my last relationship?",
	"I drink to forget __________.",
	"What is Batman	s guilty pleasure?",
	"What`s a girl`s best friend?",
	"TSA guidelines now prohibit __________ on airplanes.",
	"__________. That`s how I want to die.",
	"What`s the most emo?",
	"__________. High five, bro!",
	"White people like __________.",
	"BILLY MAYS HERE FOR __________"
];

function generateJson() {
	var inputJson = {
		state: 0,
		role: "PM",
		questionCard: "",
		yourCard: "",
		playedCards: [],
		playerCards: []
	}
	
	inputJson.questionCard = defaultQuestionsPack[Math.floor(Math.random() * defaultQuestionsPack.length)];
	
	for (var i = 0; i < Math.floor(Math.random() * 10); i++) {
		inputJson.playedCards.push("");
	}
	
	for (var i = 0; i < 7; i++) {
		var card = defaultCardPack[Math.floor(Math.random() * defaultCardPack.length)];
		while (inputJson.playerCards.indexOf(card) !== -1) { card = defaultCardPack[Math.floor(Math.random() * defaultCardPack.length)]; }
		inputJson.playerCards.push(card);
	}
	
	return inputJson;
}

function loadGame(inputJson) {
	if (inputJson.hasOwnProperty('questionCard')) {
		questionCard = inputJson.questionCard;
	}
	
	if (inputJson.hasOwnProperty('playedCards')) {
		playedCards = inputJson.playedCards;
	}
	
	if (inputJson.hasOwnProperty('playerCards')) {
		playerCards = inputJson.playedCards;
	}
}

var json = generateJson();

var playedCards = {cards : []};
var playerCards = {cards: []};
var questionCard = "";
var yourCard = "";
var cardAdded = false;
var cardPlay = false;
var role = 0;
var playerName = "Serlog";
var hasSwitched = false;
var cardIndex = -1;

var checkHasSwitched = false;

var currentSelection = 0;

var title = "Waiting for players (1/3)";

var timer;

function addCard(text) {
		playedCards.cards.push(text);
}

function addPlayerCard(text) {
	playerCards.cards.push(text);
}

function setQuestionCard(text) {
	questionCard = text;
}

function setYourCard(index) {
	yourCard = playerCards.cards[index];
}

function playCard(element, thing) {
    if (role == 1) return;
	var index = parseInt(element.target.id);
	if (index < 0 || index >= playerCards.cards.length || cardPlay) { return; }
	setYourCard(index);
	cardIndex = index;
	thing.forceUpdate();
}

function confirmSelection(ev, thing) {
    if (role == 1 || yourCard == "" || cardPlay || cardIndex == -1) { return; }

    if (timer != null) {clearInterval(timer)}
    fetch('http://localhost:8080/game/playcard?lobbyNr=' + 0 + '&userName=' + document.getElementById("playerNameInput").value + "&cardIndex=" + cardIndex, {
        'method': "GET",
    }).then(response => response.json())
        .then(data => {
            playerCards.cards = [];
            for (var i = 0; i < data.length; i++) {
                playerCards.cards.push(data[i]);
            }
            yourCard = "";
        });
    updateScreen(thing);
    cardPlay = true;
    setTimer(thing);
    //switchToCardCzar(thing);
}

function updateScreen(thing) {

    fetch('http://localhost:8080/game/getdeck?lobbyNr=' + 0 + '&userName=' + document.getElementById("playerNameInput").value, {
        'method': "GET",

    }).then(response => response.json())
        .then(data => {
            //console.log("New Field Input");
            //console.log(data);
            if (timer == null) return;
            playedCards.cards = [];
            playedCards.cards = [];
            if (data.role != null) role = data.role;
            if (data.playedCards == "[]") {
                cardPlay = false;
                //yourCard = "";
            }
            if (data.playedCards != null && data.playedCards != "[]") playedCards.cards = data.playedCards.replace("[", "").replace("]", "").split(",");
            if (data.playerCards != null && data.playerCards != "[]") playerCards.cards = data.playerCards.replace("[", "").replace("]", "").split(",");
            if (data.questionCard != null) questionCard = data.questionCard;
            if (data.gameTitle != null) title = data.gameTitle;
            if (role == 1) {
                var list = document.getElementById('answerCardsSpot');
                for (var i = 0; i < list.childNodes[0].childNodes.length; i++) {
                    //console.log(list.childNodes[0].childNodes[i].childNodes[0]);
                    list.childNodes[0].childNodes[i].childNodes[0].style.cursor = 'pointer';
                    list.childNodes[0].childNodes[i].childNodes[0].addEventListener('click', function(event) {

                        var text = event.target.childNodes[0].innerHTML;
                        if (text == null) {
                            text = event.target.innerHTML;
                        }
                        yourCard = text;

                        thing.forceUpdate();
                    });
                    list.childNodes[0].childNodes[i].childNodes[0].childNodes[0].style.cursor = 'pointer';
                }
                if (!checkHasSwitched) {
                    checkHasSwitched = true;
                    document.getElementById('confirmBtn').addEventListener('click', sendWinnerData);
                }
            }
            thing.forceUpdate();
        })
}

function sendWinnerData() {
    var index = -1;
    var list = document.getElementById("answerCardsSpot").childNodes[0];
    //console.log(list.childNodes);
    for (var i = 0 ; i < list.childNodes.length; i++) {
        if (yourCard == list.childNodes[i].childNodes[0].childNodes[0].innerHTML) {
            index = i;
            break;
        }
    }
    fetch('http://localhost:8080/game/pickWinner?lobbyNr=' + 0 + '&userName=' + document.getElementById("playerNameInput").value + "&cardIndex=" + index , {
        'method': "GET"
    });
}

function newRound(thing) {
    if (timer != null) {clearInterval(timer)}
    fetch('http://localhost:8080/game/getroundinfo?lobbyNr=' + 0 + '&userName=' + document.getElementById("playerNameInput").value , {
        'method': "GET"
    }).then(response => response.json())
        .then(data => {
            //console.log("New Round");
            //console.log(data);
            questionCard = data.questionCard;
            if (data.playerCards != null && data.playerCards != "[]") playerCards.cards = data.playerCards.replace("[", "").replace("]", "").split(",");
            role = data.role;
            thing.forceUpdate();
            setTimer(thing);
        })
}

function setTimer(thing) {
    timer = setInterval(function() {
        updateScreen(thing);
    }, 250);
}

function addUser() {
    const userName = document.getElementById("playerNameInput").value;

    fetch('http://localhost:8080/user/add?lobbyNr=' + 0 + '&userName=' + userName, {
        'method': "GET"
    })//.then(response => response.json())
}

function playGame(ev, thing) {
    var results = {data: {'kek': 'woah'}};

    var p = document.getElementById('playerNameInput').value;
    if (p.length == 0) { return; }
    addUser();
    setTimeout(function () {
        newRound(thing);
    }, 500);
    setTimer(thing);
    document.getElementById('nameSelect').style.display = 'none';
    document.getElementById('gameSpace').style.display = 'block';
    playerName = p;
    thing.forceUpdate();
}

function switchToCardCzar(thing) {
    if (hasSwitched) { return; }
    hasSwitched  = true;
    role = 1;
    document.getElementById('ShowcaseCardTitle').innerHTML = 'Selection';
    document.getElementById('ShowcaseCardTitle').style.paddingLeft = '10px';
    playedCards.cards.unshift(yourCard);
    yourCard = "";
    for (var i = 1; i < playedCards.cards.length; i++) {
        var card = defaultCardPack[Math.floor(Math.random() * defaultCardPack.length)];
        while (playedCards.cards.indexOf(card) !== -1) { card = defaultCardPack[Math.floor(Math.random() * defaultCardPack.length)]; }
        playedCards.cards.splice(i, 1, card);
    }
    cardPlay = false;
    cardAdded = false;
    var list = document.getElementById('answerCardsSpot');
    for (var i = 0; i < list.childNodes[0].childNodes.length; i++) {
        //console.log(list.childNodes[0].childNodes[i].childNodes[0]);
        list.childNodes[0].childNodes[i].childNodes[0].style.cursor = 'pointer';
        list.childNodes[0].childNodes[i].childNodes[0].addEventListener('click', function(event) {

        	if (role != 1) return;

            var text = event.target.childNodes[0].innerHTML;
            if (text == null) {
                text = event.target.innerHTML;
            }
            yourCard = text;
            thing.forceUpdate();
        })
        list.childNodes[0].childNodes[i].childNodes[0].childNodes[0].style.cursor = 'pointer';
    }
    title = 'You are the Card Czar! Choose the winner!';

    document.getElementById('confirmBtn').addEventListener('click', function(event) {
        var cardText = document.getElementById('selectedCard').innerHTML;
        if (cardText.length == 0) { return; }
        playedCards = {cards: [cardText]};
        title = 'The winner is:';
        thing.forceUpdate();
    });

    thing.forceUpdate();
}

class App extends Component {
  
  render() {
    var page = (

      <div>
        <div className="gameSpace" id="nameSelect">
        <div className="gameHeader" style={{width: '100%'}}>
        				<div className="headerTitle" style={{width: '100%', marginLeft: 'auto', marginRight: 'auto'}}>Cards against Humanity</div>
        			</div>
            <div style={{marginLeft: 'auto', marginRight: 'auto', width: '700px', height: '300px', backgroundColor: 'white', borderRadius: '3px', position: 'relative', top: '250px', overflow: 'hidden'}}>
                <div style={{marginLeft: 'auto', marginRight: 'auto', width: '260px', color: 'black', fontFamily: 'Arial', fontSize: '150%', marginTop: '50px'}}>Enter your player name:</div>
                <div style={{marginLeft: 'auto', marginRight: 'auto', width: '225px', marginTop: '20px'}}>
                    <input type="text" id="playerNameInput" autoComplete="off" autoFocus style={{width: '200px', height: '30px', fontSize: '150%', fontFamily: 'Arial', marginLeft: 'auto', marginRight: 'auto', float: 'left'}}/>
                    <button type="button" onClick={this._handleClicks} style={{float: 'left', width: '20px', height: '36px'}}>></button>
                </div>
            </div>
        </div>
          <div id="startGameBtn" onClick={this._handleDemClicks} style={{position: 'absolute', left: '0', right: '0', margin: 'auto', top: '100px', width: '150px', height: '20px', backgroundColor: 'black', color:'white', fontFamily:'Arial', textAlign:'center', fontSize:'110%', paddingTop:'1px', borderBottomLeftRadius:'8px', borderBottomRightRadius:'8px', cursor:'pointer'}}>START GAME</div>
		<div className="gameSpace" id="gameSpace" style={{display: 'none'}}>
			<div className="gameHeader">
                <div className="headerPlayerName">{playerName}</div>
				<div className="headerThing"></div>
				<div className="headerTitle">{title}</div>
			</div>
			<div className="gameField">
				<div className="gameFieldTop">
					<div className="gameFieldTopLeft" style={{height: '500px', width:'290px', float:'left'}}>
					<div className="questionCardSpot">
						<div className="cardBack" style={{backgroundColor: '#252525',marginRight: '0px', cursor: 'default'}}>
							<div className="cardTextSpot" style={{color:'white',textShadow: '2px 2px black',cursor: 'default'}}>{questionCard}</div>
						</div>
					</div>
					<div className="yourCardSpot" style={{float:'left',position: 'relative', bottom:'20px'}}>
						<div id="ShowcaseCardTitle" style={{position: 'relative', zIndex: '10', backgroundColor: '#e0e0d9', bottom: '10px',width: '75px',marginLeft: 'auto', marginRight: 'auto',fontFamily: 'Arial',paddingLeft: '4px', cursor: 'default'}}> Your Card</div>
<div className="cardBack" style={{margin: '0px',marginLeft: '15px', marginTop: '8px',position: 'relative', bottom: '10px', cursor: 'default'}}>
	<div className="deckCardBack" style={{cursor: 'default'}}><div id="selectedCard" className="cardTextSpot" style={{cursor: 'default'}}>{yourCard}</div></div>
						</div>
					</div>
					</div>
					<div id="answerCardsSpot" className="answerCardsSpot"style={{float:'right', width:'calc(100% - 350px)'}}>
  <ul>{playedCards.cards.map(function(listvalue) { return (<li><div className="cardBack" style={{cursor: 'default'}}><div className="cardTextSpot" style={{cursor: 'default'}}>{listvalue}</div></div></li>); })}</ul>
					</div>
				</div>
				<div className="gameFieldBottom">
<div style={{width: '100px', height: '25px', textAlign: 'center', color: 'white', fontFamily: 'Arial', marginLeft: '125px'}}><div id='confirmBtn' onClick={this._handleClickss} style={{height: '20px',paddingTop: '3px', position: 'relative', bottom: '13px', backgroundColor: 'rgb(37, 37, 37)', borderRadius: '0px 0px 5px 5px', cursor: 'pointer'}}>CONFIRM</div></div>
		  <div style={{width:'100%',backgroundColor: '#111111',height:'2px',clear:'both',marginBottom:'25px'}}></div>
<ul id="playerCardsSpot" style={{marginLeft:'50px'}}>
  {playerCards.cards.map( function(listvalue) { return <li><div className="deckCardBack" onClick={this._handleClick} id={playerCards.cards.indexOf(listvalue)}><div className="cardTextSpot" id={playerCards.cards.indexOf(listvalue)}>{listvalue}</div></div></li>}.bind(this))}
				</ul>
				</div>
			</div>
		</div>

	  
	  </div>
    );
	return page;
  }

  _handleDemClicks = (ev) => {
      console.log(parseInt(title.replace("Waiting for players (", "").replace("/3)", "")));
      if (parseInt(title.replace("Waiting for players (", "").replace("/3)", "")) >= 3) {
          document.getElementById("startGameBtn").style.display = 'none';
          fetch('http://localhost:8080/game/start?lobbyNr=' + 0 + '&userName=' + document.getElementById("playerNameInput").value, {
              'method': "GET"
          })
      }
  }
  
  _handleClick = (ev) => {
  	if (role == 0) playCard(ev, this);
  }

  _handleClicks = (ev) => {
      playGame(ev, this);
   }

   _handleClickss = (ev) => {
      confirmSelection(ev, this);
   }
}

export default App;

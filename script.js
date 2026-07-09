async function getWeather() {

    const city = document.getElementById("city").value.trim();

    if(city === ""){
        document.getElementById("result").innerHTML =
        "<p>Please enter a city name</p>";
        return;
    }

    const apiKey = "6eb28a562adef40aa2c6440e0814c338";

    const url =
    `https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${apiKey}&units=metric`;

    try{

        const response = await fetch(url);

        const data = await response.json();

        if(data.cod != 200){

            document.getElementById("result").innerHTML =
            `<p>${data.message}</p>`;

            return;
        }

        const sunrise =
        new Date(data.sys.sunrise * 1000).toLocaleTimeString();

        const sunset =
        new Date(data.sys.sunset * 1000).toLocaleTimeString();

        document.getElementById("result").innerHTML = `
        
        <h2>${data.name}</h2>

        <h1>${data.main.temp}°C</h1>

        <p>🌥 ${data.weather[0].description}</p>

        <p>💧 Humidity: ${data.main.humidity}%</p>

        <p>👀 Visibility: ${data.visibility / 1000} km</p>

        <p>🌅 Sunrise: ${sunrise}</p>

        <p>🌇 Sunset: ${sunset}</p>

        `;

    }

    catch(error){

        document.getElementById("result").innerHTML =
        "<p>Something went wrong</p>";

        console.log(error);
    }
}
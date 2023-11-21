# Temperaturdaten analysieren**

In einem Gebäude gibt es Sensoren, die Temperaturwerte in unregelmäßigen Zeitabständen messen. Zur Auswertung der 
Messwerte soll eine Klasse `TemperatureAnalyzer` mit der Methode `public int getMaxPeriod(double[] temperatureList, double minValue)` 
implementiert werden, die aus allen in einem ihr übergebenen Array gespeicherten Temperaturwerten die höchste Anzahl von 
hintereinander gespeicherten Werten des Sensors ermittelt, welche den  vorgegebenen Mindestwert (minValue) einhalten.  

**Beispiel**  
Es liegen die Temperaturwerte 20, 22, 23, 21, 19, 18, 20, <b>22, 23, 23, 24, 22</b>, 21 vor.  
Die höchste Anzahl von hintereinander liegenden Werten, welche den Mindestwert 22 einhalten, ist 5.



###Aufgaben

1. Implementiere in der Klasse `TemperatureAnalyzer` die oben beschriebene Methode
`public int getMaxPeriod(double[] temperatureList, double minValue)`.
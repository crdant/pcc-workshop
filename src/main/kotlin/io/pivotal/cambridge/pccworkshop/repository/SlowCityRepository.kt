package io.pivotal.cambridge.pccworkshop.repository

class SlowCityRepository ( delegate : CityRepository ) : CityRepository by delegate {
}
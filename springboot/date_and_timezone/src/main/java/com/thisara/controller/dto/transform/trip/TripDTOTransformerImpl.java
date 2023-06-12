package com.thisara.controller.dto.transform.trip;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thisara.controller.dto.trip.CarDTO;
import com.thisara.controller.dto.trip.GetTripResponse;
import com.thisara.domain.Car;
import com.thisara.domain.Trip;

@Component
public class TripDTOTransformerImpl implements TripDTOTransformer {

	@Autowired
	public ModelMapper modelMapper;

	public Logger logger = Logger.getLogger(TripDTOTransformerImpl.class.getName());

	@Value("${trip.datetime.pattern}")
	private String dateTimePattern;

	@Override
	public List<GetTripResponse> carPageTripList(List<Trip> tripList, String timezone) {

		modelMapper.getConfiguration().setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
				.setFieldMatchingEnabled(true).setMatchingStrategy(MatchingStrategies.STANDARD);

		Converter<String, String> caseConverter = new Converter<String, String>() {
			public String convert(MappingContext<String, String> context) {
				return context.getSource() == null ? null : context.getSource().toUpperCase();
			}
		};

		Converter<String, String> chassisNumberConverter = new Converter<String, String>() {

			String fillingChar = "0";
			String chasisNumber;
			int standardLength = 12;

			public String convert(MappingContext<String, String> context) {

				chasisNumber = context.getSource();

				if (chasisNumber != null && chasisNumber.length() < standardLength) {

					int charsToPrefixed = standardLength - chasisNumber.length();

					while (charsToPrefixed != 0) {
						chasisNumber = fillingChar + chasisNumber;
						charsToPrefixed--;
					}
				}

				return chasisNumber;
			}
		};

		Converter<ZonedDateTime, String> timezoneConverter = new Converter<ZonedDateTime, String>() {

			String actualTime = null;

			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);

			public String convert(MappingContext<ZonedDateTime, String> context) {

				actualTime = context.getSource().toInstant().atZone(ZoneId.of(timezone)).format(dateTimeFormatter);

				return actualTime;
			}
		};

		Converter<ZonedDateTime, String> recordAgeConverter = new Converter<ZonedDateTime, String>() {

			String recordAge = null;

			long years;
			long months;
			long days;
			long hours;
			long minutes;
			long seconds;

			public String convert(MappingContext<ZonedDateTime, String> context) {

				LocalDateTime recordDateTime = context.getSource().toInstant().atZone(ZoneId.of(timezone))
						.toLocalDateTime();

				LocalDateTime currentDateTime  = LocalDateTime.now(ZoneId.of(timezone));

				years = ChronoUnit.YEARS.between(recordDateTime, currentDateTime);
				months = ChronoUnit.MONTHS.between(recordDateTime, currentDateTime);
				days = ChronoUnit.DAYS.between(recordDateTime, currentDateTime);
				hours = ChronoUnit.HOURS.between(recordDateTime, currentDateTime);
				minutes = ChronoUnit.MINUTES.between(recordDateTime, currentDateTime);
				seconds = ChronoUnit.SECONDS.between(recordDateTime, currentDateTime);

				if (years > 0) {
					recordAge = years + " years";
				} else if (months > 0) {
					recordAge = months + " months";
				} else if (days > 0) {
					recordAge = days + " days";
				} else if (hours > 0) {
					recordAge = hours + " hours";
				} else if (minutes > 0) {
					recordAge = minutes + " minutes";
				} else if (seconds > 0) {
					recordAge = seconds + " seconds";
				}

				return recordAge;
			}
		};

		TypeMap<Car, CarDTO> carTypeMap = modelMapper.getTypeMap(Car.class, CarDTO.class);

		if (carTypeMap == null) {
			carTypeMap = modelMapper.createTypeMap(Car.class, CarDTO.class);
		}

		carTypeMap.addMappings(
				mappings -> mappings.using(chassisNumberConverter).map(Car::getChasisNumber, CarDTO::setChasisNumber));
		carTypeMap.addMappings(mappings -> mappings.skip(CarDTO::setChasisColor));

		TypeMap<Trip, GetTripResponse> tripTypeMap = modelMapper.getTypeMap(Trip.class, GetTripResponse.class);

		if (tripTypeMap == null) {
			tripTypeMap = modelMapper.createTypeMap(Trip.class, GetTripResponse.class);
		}

		tripTypeMap.addMappings(mappings -> mappings.using(timezoneConverter).map(Trip::getActualTripTimestamp,
				GetTripResponse::setRecordTimestamp));
		tripTypeMap.addMappings(mappings -> mappings.using(recordAgeConverter).map(Trip::getActualTripTimestamp,
				GetTripResponse::setRecordAge));

		modelMapper.addConverter(caseConverter);

		List<GetTripResponse> tripDTOs = tripList.stream().map(source -> modelMapper.map(source, GetTripResponse.class))
				.collect(Collectors.toList());

		return tripDTOs;
	}

}

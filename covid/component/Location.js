import React from 'react'
import {
  View,
  Button,
  TextInput,
  Text,
  StyleSheet,
  TouchableOpacity
} from 'react-native'
import { Platform } from '@unimodules/core';
// import { Permissions }  from 'expo-location';
import * as Permissions from 'expo-permissions';

export default class Location extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      latitude: null,
      longitude: null,
      routeCoordinates: [],
      distanceTravelled: 0,
      prevLatLng: {},
      // coordinate: new AnimatedRegion({
      //   latitude: null,
      //   longitude: null
      // })
    };
  }


  componentDidMount() {
    this.checkPermission()
  }

  checkPermission = async () => {
    let { status } = await Permissions.askAsync(Permissions.LOCATION);

    if (status !== 'granted') {
      this.setState({
        errorMessage: 'Permission to access location was denied',
      });
      console.log("no permission");
    }
    this.getCurrentLocation();
  }

  getCurrentLocation = () => {
    this.watchID = navigator.geolocation.watchPosition(
      position => {
        const { coordinate, routeCoordinates, distanceTravelled } = this.state;
        const { latitude, longitude } = position.coords;

        const newCoordinate = {
          latitude,
          longitude
        };
        if (Platform.OS === "android") {
          if (this.marker) {
            this.marker._component.animateMarkerToCoordinate(
              newCoordinate,
              500
            );
          }
        } else {
          // coordinate.timing(newCoordinate).start();
        }
        this.setState({
          latitude,
          longitude,
          routeCoordinates: routeCoordinates.concat([newCoordinate]),
          // distanceTravelled:
          //   distanceTravelled + this.calcDistance(newCoordinate),
          prevLatLng: newCoordinate
        });
      },
      error => console.log(error),
      { enableHighAccuracy: true, timeout: 20000, maximumAge: 1000 }
    );

  }

  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity style={[styles.container, styles.container]}>
          <Text style={styles.container}>
            {(this.state.latitude)} {(this.state.longitude)}
          </Text>
        </TouchableOpacity>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  input: {
    width: 350,
    height: 55,
    backgroundColor: '#42A5F5',
    margin: 10,
    padding: 8,
    color: 'white',
    borderRadius: 14,
    fontSize: 18,
    fontWeight: '500',
  },
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center'
  }
})
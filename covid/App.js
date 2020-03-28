import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import Registration from './component/Registration';
import Location from './component/Location';
import Service from './component/Service';


export default class App extends React.Component {

  constructor() {
    super()
    this.service = new Service();
    this.service.startService();
  }

  render() {
    return (
      <View style={styles.container}>
        <Registration/>
        <Location/>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

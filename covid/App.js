import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import Registration from './component/Registration';
import Location from './component/Location';


export default class App extends React.Component {

  constructor() {
    super()
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

import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import Registration from './component/Registration';
import Location from './component/Location';
import AsyncStorage from '@react-native-community/async-storage';


export default class App extends React.Component {

  constructor(){
    super()
    this._retrieveData()
  }

  state = {
    username: '', email: '', phone_number: ''
  }

  // fetch the data back asyncronously
  _retrieveData = async () => {
    try {
      const username = await AsyncStorage.getItem('username');
      const email = await AsyncStorage.getItem('email');
      const phone_number = await AsyncStorage.getItem('phone_number');
      this.setState({username, email, phone_number })
      console.log(username, email, phone_number)
    } catch (error) {
      console.log(error)
    }
  }

  render() {
    const { username, email, phone_number } = this.state;
    console.log(this.state, (username.length || email.length || phone_number.length))
    return (
      <View style={styles.container}>
        {
          !(username.length || email.length || phone_number.length)
          ?
          <Registration />
          :
          <Location />
        }
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

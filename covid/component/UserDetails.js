import React from 'react'
import {
  View,
  Button,
  TextInput,
  StyleSheet,
  AsyncStorage
} from 'react-native'

export default class UserDetails extends React.Component {
  state = {
    username: '', password: '', email: '', phone_number: ''
  }

  onChangeText = (key, val) => {
    this.setState({ [key]: val })
  }

  signUp = async () => {
    debugger
    console.log("hello")
    const { username, email, phone_number } = this.state
    try {
        await AsyncStorage.setItem('username', username);
        await AsyncStorage.setItem('email', email);
        await AsyncStorage.setItem('phone_number', phone_number);
        console.log('user successfully signed up!: ', await AsyncStorage.get('username'))
    } catch (err) {
      console.log('error signing up: ', err)
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <TextInput
          style={styles.input}
          placeholder='Name'
          autoCapitalize="none"
          placeholderTextColor='white'
          onChangeText={val => this.onChangeText('username', val)}
        />
        <TextInput
          style={styles.input}
          placeholder='Email'
          autoCapitalize="none"
          placeholderTextColor='white'
          onChangeText={val => this.onChangeText('email', val)}
        />
        <TextInput
          style={styles.input}
          placeholder='Phone Number'
          autoCapitalize="none"
          placeholderTextColor='white'
          onChangeText={val => this.onChangeText('phone_number', val)}
        />
        <Button
          title='Submit'
          onPress={this.signUp}
        />
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
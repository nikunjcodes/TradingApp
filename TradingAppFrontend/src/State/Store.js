import { legacy_createStore, applyMiddleware } from "redux";
import { combineReducers } from "redux";
import authReducer from "./Auth/Reducer";
import { thunk } from "redux-thunk";
import coinReducer from "./Coin/Reducer";
const rootReducer = combineReducers({
  auth: authReducer,
  coin: coinReducer,
});
export const store = legacy_createStore(rootReducer, applyMiddleware(thunk));

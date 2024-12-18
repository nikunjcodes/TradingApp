const {
  combineReducer,
  legacy_createStore,
  applyMiddleware,
} = require("redux");
import { thunk } from "redux-thunk";
const rootReducer = combineReducer({});
export const store = legacy_createStore(rootReducer, applyMiddleware(thunk));

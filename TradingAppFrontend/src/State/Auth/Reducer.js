import {
  REGISTER_REQUEST,
  REGISTER_FAILURE,
  LOGIN_FAILURE,
  GET_USER_FAILURE,
  LOGIN_SUCCESS,
  GET_USER_SUCCESS,
  LOGIN_REQUEST,
  GET_USER_REQUEST,
  REGISTER_SUCCESS,
  LOGOUT,
} from "./ActionType";

const initialState = {
  user: null,
  loading: false,
  error: null,
  jwt: null,
};
const authReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOGOUT:
      return { initialState };
    case REGISTER_REQUEST:
    case LOGIN_REQUEST:
    case GET_USER_REQUEST:
      return { ...state, loading: true, error: null };
    case REGISTER_SUCCESS:
    case LOGIN_SUCCESS:
      return { ...state, loading: false, error: null, jwt: action.payload };
    case GET_USER_SUCCESS:
      return { ...state, loading: false, error: null, user: action.payload };
    case REGISTER_FAILURE:
    case LOGIN_FAILURE:
    case GET_USER_FAILURE:
      return { ...state, loading: false, error: action.payload };
    default:
      return state;
  }
};
export default authReducer;

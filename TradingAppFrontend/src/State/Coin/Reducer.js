import {
  FETCH_COIN_LIST_REQUEST,
  FETCH_COIN_LIST_SUCCESS,
  FETCH_COIN_LIST_FAILURE,
  FETCH_TOP_50_COINS_REQUEST,
  FETCH_TOP_50_COINS_SUCCESS,
  FETCH_TOP_50_COINS_FAILURE,
  FETCH_MARKET_CHART_REQUEST,
  FETCH_MARKET_CHART_SUCCESS,
  FETCH_MARKET_CHART_FAILURE,
  FETCH_COIN_BY_ID_REQUEST,
  FETCH_COIN_BY_ID_SUCCESS,
  FETCH_COIN_BY_ID_FAILURE,
  FETCH_COIN_DETAILS_REQUEST,
  FETCH_COIN_DETAILS_SUCCESS,
  FETCH_COIN_DETAILS_FAILURE,
  SEARCH_COIN_REQUEST,
  SEARCH_COIN_SUCCESS,
  SEARCH_COIN_FAILURE,
} from "./ActionType";
const initialState = {
  coinList: [],
  top50Coins: [],
  marketChart: [],
  coinById: null,
  coinDetails: null,
  searchCoinList: [],
  loading: false,
  error: null,
};
const coinReducer = (state = initialState, action) => {
  switch (action.type) {
    case FETCH_COIN_LIST_REQUEST:
    case FETCH_TOP_50_COINS_REQUEST:
    case FETCH_COIN_BY_ID_REQUEST:
    case FETCH_COIN_DETAILS_REQUEST:
    case SEARCH_COIN_REQUEST:
      return { ...state, loading: true, error: null };
    case FETCH_MARKET_CHART_REQUEST:
      return {
        ...state,
        marketChart: { loading: true, data: [] },
        error: null,
      };
    case FETCH_COIN_LIST_SUCCESS:
      return {
        ...state,
        coinList: { loading: false, data: action.payload },
        error: null,
      };
    case FETCH_TOP_50_COINS_SUCCESS:
      return {
        ...state,
        top50Coins: { loading: false, data: action.payload },
        error: null,
      };
    case FETCH_MARKET_CHART_SUCCESS:
      return {
        ...state,
        marketChart: { loading: false, data: action.payload.prices },
        error: null,
      };
    case FETCH_COIN_BY_ID_SUCCESS:
      return {
        ...state,
        coinById: { loading: false, data: action.payload },
        error: null,
      };
    case FETCH_COIN_DETAILS_SUCCESS:
      return {
        ...state,
        coinDetails: { loading: false, data: action.payload },
        error: null,
      };
    case SEARCH_COIN_SUCCESS:
      return {
        ...state,
        searchCoinList: { loading: false, data: action.payload.coins },
        error: null,
      };
    case FETCH_MARKET_CHART_FAILURE:
      return {
        ...state,
        marketChart: { loading: false, error: action.payload },
      };

    case FETCH_COIN_LIST_FAILURE:
    case FETCH_TOP_50_COINS_FAILURE:
    case FETCH_COIN_BY_ID_FAILURE:
    case FETCH_COIN_DETAILS_FAILURE:
    case SEARCH_COIN_FAILURE:
      return {
        ...state,
        loading: false,
        error: action.payload,
      };
    default:
      return state;
  }
};
export default coinReducer;

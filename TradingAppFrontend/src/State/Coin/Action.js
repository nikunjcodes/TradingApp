import axios from "axios";
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
import api, { API_BASE_URL } from "../../config/api";
const baseUrl = API_BASE_URL;
export const getCoinList = (page) => async (dispatch) => {
  dispatch({ type: FETCH_COIN_LIST_REQUEST });
  try {
    const data = await axios.get(`${baseUrl}/coins?page=${page}`);
    console.log("coin list" + data);
    dispatch({ type: FETCH_COIN_LIST_SUCCESS, payload: data });
  } catch (error) {
    dispatch({ type: FETCH_COIN_LIST_FAILURE, payload: error.message });
    console.log(error);
  }
};
export const getTop50Coins = () => async (dispatch) => {
  dispatch({ type: FETCH_TOP_50_COINS_REQUEST });
  try {
    const response = await axios.get(`${baseUrl}/coins/top50`);
    dispatch({ type: FETCH_TOP_50_COINS_SUCCESS, payload: response.data });
  } catch (error) {
    dispatch({ type: FETCH_TOP_50_COINS_FAILURE, payload: error.message });
    console.log(error);
  }
};
export const fetchMarketChart = (coinId, days, jwt) => async (dispatch) => {
  dispatch({ type: FETCH_MARKET_CHART_REQUEST });
  try {
    const response = await api.get(`/coins/${coinId}/chart?days=${days}`, {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    });
    dispatch({ type: FETCH_MARKET_CHART_SUCCESS, payload: response.data });
  } catch (error) {
    dispatch({ type: FETCH_MARKET_CHART_FAILURE, payload: error.message });
    console.log(error);
  }
};
export const fetchCoinById = (coinId) => async (dispatch) => {
  dispatch({ type: FETCH_COIN_BY_ID_REQUEST });

  try {
    const response = await axios.get(`${baseUrl}/coins/${coinId}`);
    dispatch({ type: FETCH_COIN_BY_ID_SUCCESS, payload: response.data });
  } catch (error) {
    dispatch({ type: FETCH_COIN_BY_ID_FAILURE, payload: error.message });
    console.log(error);
  }
};
export const fetchCoinDetails = (coinId) => async (dispatch) => {
  dispatch({ type: FETCH_COIN_DETAILS_REQUEST });
  try {
    const response = await api.get(`/coins/details/${coinId}`, {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    });
  } catch (error) {
    dispatch({ type: FETCH_COIN_DETAILS_FAILURE, payload: error.message });
    console.log(error);
  }
};
export const searchCoin = (query) => async (dispatch) => {
  dispatch({ type: SEARCH_COIN_REQUEST });
  try {
    const reponse = await api.get(`/coins/search?q=${query}`);
    dispatch({ type: SEARCH_COIN_SUCCESS, payload: reponse.data });
  } catch (error) {
    dispatch({ type: SEARCH_COIN_FAILURE, payload: error.message });
    console.log(error);
  }
};

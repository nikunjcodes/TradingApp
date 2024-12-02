import { Button } from '@/components/ui/button'
import React from 'react'
import AssetTable from './AssetTable'
import StockChart from './StockChart'
import { Avatar, AvatarImage } from 'radix-ui'
import { Crosshair, CrossIcon, DotIcon, MessageCircle } from 'lucide-react'
import { Cross1Icon } from '@radix-ui/react-icons'

const Home = () => {
  const [category , setCategroy] = React.useState("all")
  const handleCategory = (category) => {
    setCategroy(category)
  }
  return (
    <div className='relative'>
      <div className='lg:flex'>
        <div className='lg:w-[50%] lg:border-r'>
          <div className='flex items-center p-3 gap-4'>
            <Button onClick = {()=>handleCategory("all")}
            variant={category=="all" ? "default":"outline"} className='rounded-full'>
              All
            </Button>
            <Button onClick = {()=>handleCategory("top50")}
            variant={category=="top50" ? "default":"outline"} className='rounded-full'>
              Tpp 50
            </Button>
            <Button onClick = {()=>handleCategory("topGainers")}
            variant={category=="topGainers" ? "default":"outline"} className='rounded-full'>
              Top Gainers
            </Button>
            <Button onClick = {()=>handleCategory("topLosers")}
            variant={category=="topLosers" ? "default":"outline"} className='rounded-full'>
              Top Losers
            </Button>

          </div>
          <AssetTable />
        </div>
        <div className='hidden lg:block lg:w-[50%] p-5'>
          <StockChart/>
          <div className='flex gap-5 items-center'>
            <Avatar>
              <AvatarImage src="https://assets.coingecko.com/coins/images/1/standard/bitcoin.png?1696501400" />
            </Avatar>
          </div>
          <div>
          <div className='flex items-center gap-2'>
              <p>BTC</p>
              <DotIcon className='text-gray-400'/>
              <p className='text-gray-400'>Bitcoin</p>
          </div>
          <div className='flex items-end gap-2'>
            <p className='text-xl font-bold'>
              5464
            </p>
            <p className='text-red-600'>
              <span>-1319049822.578</span>
              <span>(-0.29803%)</span>
            </p>
          </div>
          </div>
        </div>
      </div>
      <section className='absolute bottom-5 right-5 z-40 flex flex-col justify-end items-end gap-2'>
        <div className='rounded-md w-[20rem] md:w-[25rem] lg:w-[25rem] h-[70vh] bg-slate-900'>
         <div className='flex justify-between items-center border-b px-6 h-[12%]'>
          <p>Chat Bot</p>
          <Button variant = "ghost" size="icon">
            <Cross1Icon/>
          </Button>
          </div>
          <div className='h-[76%] flex flex-col overflow-y-auto gap-5 px-5 py-2 scroll-container'>
            <div className='self-start pb-5 w-auto'>
              <div className='justify-end self-end px-5 py-2 rounded-md bg-slate-800 w-auto text-gray-400'>
                <p >Bot</p>
                <p>Hi, How can I help you?</p>
                <p>you can ask crypto related questions</p>

              </div>
              </div>
              <div className='self-start pb-5 w-auto'>
              <div className='justify-end self-end px-5 py-2 rounded-md bg-slate-800 w-auto text-gray-400'>
                <p >Prompt who are you ?</p>

              </div>
              </div>
              <div className='self-start pb-5 w-auto'>
              <div className='justify-end self-end px-5 py-2 rounded-md bg-slate-800 w-auto text-gray-400'>
                <p >Answerr is me Nikunj</p>


              </div>
              </div>
          </div>
        </div>
        
        <div className='relative w-[10rem] cursor-pointer group'>
          <Button className='w-full h-[3rem] gap-2 items-center'>
            <MessageCircle
            size={30} className='fill-[#1e293b] -rotate-90 stroke-none group-hover:fill-[#1a1a1a]'/>
            <span className='text2xl'>
              Chatbot
            </span>
          </Button>
        </div>
      </section>
    </div>
  )
}

export default Home
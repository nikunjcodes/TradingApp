import { Button } from '@/components/ui/button'
import React from 'react'
import AssetTable from './AssetTable'
import StockChart from './StockChart'
import { Avatar, AvatarImage } from 'radix-ui'
import { DotIcon } from 'lucide-react'
import { Cross1Icon } from '@radix-ui/react-icons'
import { MessageCircle } from 'lucide-react'
import { Input } from '@/components/ui/input'

const Home = () => {
  const [category, setCategory] = React.useState("all")
  const [inputValue, setInputValue] = React.useState("")
  const [isBotReleased, setIsBotReleased] = React.useState(false)
  
  const handleChange = (e) => {
    setInputValue(e.target.value)
  }

  const handleBotRelease = () => {
    setIsBotReleased(!isBotReleased)
  }

  const handleKeyPress = (e) => {
    if (e.key === "Enter") {
      console.log(inputValue)
      setInputValue("")
    }
  }

  const handleCategory = (category) => {
    setCategory(category)
  }

  return (
    <div className='relative'>
      <div className='lg:flex'>
        <div className='lg:w-[50%] lg:border-r'>
          <div className='flex items-center p-3 gap-4'>
            <Button 
              onClick={() => handleCategory("all")} 
              variant={category === "all" ? "default" : "outline"} 
              className='rounded-full'>
              All
            </Button>
            <Button 
              onClick={() => handleCategory("top50")} 
              variant={category === "top50" ? "default" : "outline"} 
              className='rounded-full'>
              Top 50
            </Button>
            <Button 
              onClick={() => handleCategory("topGainers")} 
              variant={category === "topGainers" ? "default" : "outline"} 
              className='rounded-full'>
              Top Gainers
            </Button>
            <Button 
              onClick={() => handleCategory("topLosers")} 
              variant={category === "topLosers" ? "default" : "outline"} 
              className='rounded-full'>
              Top Losers
            </Button>
          </div>
          <AssetTable />
        </div>
        
        <div className='hidden lg:block lg:w-[50%] p-5'>
          <StockChart />
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
        {isBotReleased && (
          <div className='rounded-md w-[20rem] md:w-[25rem] lg:w-[25rem] h-[70vh] bg-slate-900'>
            <div className='flex justify-between items-center border-b px-6 h-[12%]'>
              <p>Chat Bot</p>
              <Button onClick={handleBotRelease} variant="ghost" size="icon">
                <Cross1Icon />
              </Button>
            </div>
            <div className='h-[76%] flex flex-col overflow-y-auto gap-5 px-5 py-2'>
              <div className='self-start pb-5 w-auto'>
                <div className='justify-end self-end px-5 py-2 rounded-md bg-slate-800 w-auto'>
                  <p>Bot</p>
                  <p>Hi, How can I help you?</p>
                  <p>You can ask crypto-related questions</p>
                </div>
              </div>

              {[1, 1, 1, 1].map((item, i) => (
                <div key={i} className={`${i % 2 === 0 ? "self-start" : "self-end"} pb-5 w-auto`}>
                  {i % 2 === 0 ? (
                    <div className='justify-end self-end px-5 py-2 rounded-md bg-slate-800 w-auto'>
                      <p>Prompt: Who are you?</p>
                    </div>
                  ) : (
                    <div className='justify-end self-end px-5 py-2 rounded-md bg-slate-800 w-auto'>
                      <p>Answer: I am Nikunj</p>
                    </div>
                  )}
                </div>
              ))}
            </div>
            <div className='h-[12%] border-t'>
              <Input 
                className="w-full h-full border-none outline-none" 
                placeholder="Write prompt"
                onChange={handleChange}
                value={inputValue}
                onKeyDown={handleKeyPress}
              />
            </div>
          </div>
        )}
        
        <div className='relative w-[10rem] cursor-pointer group'>
          <Button 
            onClick={handleBotRelease}
            className='w-full h-[3rem] gap-2 items-center'>
            <MessageCircle 
              size={30} 
              className='fill-[#1e293b] -rotate-90 stroke-none group-hover:fill-[#1a1a1a]' />
            <span className='text-2xl'>
              Chatbot
            </span>
          </Button>
        </div>
      </section>
    </div>
  )
}

export default Home
